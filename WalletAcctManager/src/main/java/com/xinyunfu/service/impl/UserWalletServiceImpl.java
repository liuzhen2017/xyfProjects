package com.xinyunfu.service.impl;

import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.constant.PayType;
import com.xinyunfu.constant.Redis;
import com.xinyunfu.dto.UserInfoDTO;
import com.xinyunfu.dto.order.OrderCommodity;
import com.xinyunfu.dto.order.OrderItem;
import com.xinyunfu.dto.order.OrderMaster;
import com.xinyunfu.feign.CustomerManageFeign;
import com.xinyunfu.feign.OrderManageFeign;
import com.xinyunfu.service.AccFlowService;
import com.xinyunfu.util.RedisCommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xinyunfu.mapper.UserWalletMapper;
import com.xinyunfu.service.UserWalletService;
import com.xinyunfu.entity.UserWallet;
import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author liuzhen 用户钱包 Service
 */
@Slf4j
@Service
public class UserWalletServiceImpl extends ServiceImpl<UserWalletMapper, UserWallet> implements UserWalletService {

    @Value("${withdraw.supplierMinAmout}")
    private BigDecimal supplierMinAmout;
    @Autowired
    private RedisCommonUtil redis;
    @Autowired
    private AccFlowServiceImpl accFlowService;
    @Autowired
    private OrderManageFeign orderManageFeign;
    @Autowired
    private CustomerManageFeign customerManageFeign;


    /**
     * 给指定账户 发红包 （分润）
     * @param str  用户编号;金额,用户编号;金额
     * @return
     */
    @Override
    public boolean redEnvelopes(String str) {
        String[] list = str.split(",");
        for (String s : list) {
            String userNo = s.split(";")[0];                    //获取用户编号
            BigDecimal money = new BigDecimal(s.split(";")[1]); //获取佣金
            //根据用户编号获取 账户信息
            UserWallet user = this.getUser(userNo);
            user.setBalance(user.getBalance().add(money));
            user.setAvailableBalance(user.getAvailableBalance().add(money));
            if(!super.updateById(user))
                throw new CustomException(ExecptionEnum.REFILL_FAILED);
            accFlowService.add("-1",user.getAccountNo(),user.getAccountType(),Common.FLOW_TYPE_IN,Common.FLOW_SOURCE_TRANSFER,money,Common.STATUS_SUCCESS,"佣金收益");
        }
        return true;
    }

    /**
     * 系统用户入账
     * @param money 金额
     * @return
     */
    @Override
    @Transactional
    public synchronized boolean SystemAccountEntry(BigDecimal money) {
        UserWallet sys = super.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getAccountNo, Common.SYSTEM_ACCOUNT));
        sys.setBalance(sys.getBalance().add(money));
        sys.setAvailableBalance(sys.getAvailableBalance().add(money));
        if(!super.updateById(sys))
            throw new CustomException(ExecptionEnum.SYSTEM_ACCOUNT_ENTRY);
        accFlowService.add("-1",sys.getAccountNo(),sys.getAccountType(),Common.FLOW_TYPE_IN,Common.FLOW_SOURCE_TRANSFER,money,Common.STATUS_SUCCESS,"系统入账");
        return true;
    }

    /**
     * 系统账户 出账
     * @param accountNo    账户编号
     * @param money        金额
     * @return
     */
    @Override
    @Transactional
    public synchronized boolean SystemOutOfAccount(String accountNo, BigDecimal money) {
        UserWallet sys = super.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getAccountNo, Common.SYSTEM_ACCOUNT));
        if(sys.getAvailableBalance().compareTo(money) == -1) //判断可用余额是否充足
            throw new CustomException(ExecptionEnum.ERROR_INSUFFICIENT_BALANCE);
        sys.setBalance(sys.getBalance().subtract(money));
        sys.setAvailableBalance(sys.getAvailableBalance().subtract(money));
        if(!super.updateById(sys))
            throw new CustomException(ExecptionEnum.SYSTEM_ACCOUNT_ENTRY);
        //给指定用户转钱
        UserWallet user = super.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getAccountNo, accountNo));
        user.setBalance(user.getBalance().add(money));
        user.setAvailableBalance(user.getAvailableBalance().add(money));
        if(!super.updateById(user))
            throw new CustomException(ExecptionEnum.REFILL_FAILED);
        //记录日志
        accFlowService.add("-1",sys.getAccountNo(),sys.getAccountType(),Common.FLOW_TYPE_OUT,Common.FLOW_SOURCE_TRANSFER,money,Common.STATUS_SUCCESS,"系统出账");
        accFlowService.add("-1",user.getAccountNo(),user.getAccountType(),Common.FLOW_TYPE_IN,Common.FLOW_SOURCE_TRANSFER,money,Common.STATUS_SUCCESS,"后台支付");
        //清除缓存
        redis.clear(Redis.KEY_USER+sys.getUserNo());
        redis.clear(Redis.KEY_USER+user.getUserNo());
        log.info("[后台支付]==========>支付成功！用户编号：{}，用户姓名：{}，账户：{}，金额：{}",user.getUserNo(),user.getUserName(),accountNo,money);
        return true;
    }

    /**
     * 消费
     *     判断可用余额是否充足
     *     如果够 则将可用余额 转到 冻结金额中 结算时再做处理！
     * @param orderNo 订单编号
     * @param userNo  用户编号
     * @param money   消费金额
     * @return
     */
    @Override
    @Transactional
    public synchronized boolean consume(String orderNo, String userNo, BigDecimal money) {
        UserWallet user = this.getUser(userNo);
        if(user.getAvailableBalance().compareTo(money) == -1) return false;//判断可用余额是否充足
        user.setBalance(user.getBalance().subtract(money)); //扣减总金额
        user.setAvailableBalance(user.getAvailableBalance().subtract(money)); //扣减可用金额
        user.setFrozenBalance(user.getFrozenBalance().add(money)); //增加冻结金额
        if(!super.updateById(user))
            throw new CustomException(ExecptionEnum.ERROR_COUNPONS_FAIL);
        accFlowService.add(orderNo,user.getAccountNo(),user.getAccountType(),Common.FLOW_TYPE_OUT,Common.FLOW_SOURCE_PRODUCT,money,Common.STATUS_SUCCESS,orderNo);
        redis.clear(Redis.KEY_USER+user.getUserNo());
        log.info("[现金消费]==========>已成功扣除用户可用余额!");
//        //通过主订单编号获取所有子订单信息 （商家-金额）
//        ResponseInfo<OrderMaster> res = orderManageFeign.getOrderInfo(orderNo);
//        if(!res.isSuccess())
//            throw new CustomException(ExecptionEnum.ERROR_GET_ORDER_INFO);
//        OrderMaster master = (OrderMaster) res.getData();
//        //判断支付方式
//        BigDecimal totalCost = new BigDecimal(0); //记录总成本价格
//        BigDecimal totalPayAmout = new BigDecimal(0); //记录总支付金额
//        //获取商家账户 根据成本价格给商家账号结算
//        for (OrderItem item : master.getList()) {
//            Long storeId = item.getStoreId();//获取商家ID 即商家账户的用户编号
//            BigDecimal cost = item.getCostAmount();       //子订单的总成本价格
////            for (OrderCommodity com : item.getList()) {
////                //叠加商品的成本价格 成本价格*商品数量
////                cost = cost.add(com.getCostPrice().multiply(new BigDecimal(com.getBuyCount())));
////            }
//            totalCost = totalCost.add(cost); //叠加成本价格
//            totalPayAmout = totalPayAmout.add(item.getPayAmount()); //叠加支付金额
//            //给商家账户转账
//            UserWallet store = super.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getUserNo, storeId));
//            store.setBalance(store.getBalance().add(cost));
//            store.setAvailableBalance(store.getAvailableBalance().add(cost));
//            if(!super.updateById(store))
//                throw new CustomException(ExecptionEnum.ERROR_COUNPONS_FAIL);
//            accFlowService.add(orderNo,store.getAccountNo(),store.getAccountType(),Common.FLOW_TYPE_IN,Common.FLOW_SOURCE_PRODUCT,cost,Common.STATUS_SUCCESS,orderNo);
//            redis.clear(Redis.KEY_USER+store.getUserNo());
//            log.info("[支付消费]==========>给商家转账成功！商家ID：{}，转入金额：{}",storeId,cost);
//        }
//        //校验金额
//        if(money.compareTo(totalPayAmout) != 0)
//            throw new CustomException(ExecptionEnum.VALUE_CHECK_FAILED);
//        //给内部账户转账  利润 =  总消费金额 - 总成本金
//        BigDecimal profit = money.subtract(totalCost);
//        UserWallet sys = super.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getAccountNo, Common.INNER_ACCOUNT));
//        sys.setBalance(sys.getBalance().add(profit));
//        sys.setAvailableBalance(sys.getAvailableBalance().add(profit));
//        if(!super.updateById(sys))
//            throw new CustomException(ExecptionEnum.SYSTEM_ACCOUNT_ENTRY);
//        accFlowService.add(orderNo,sys.getAccountNo(),sys.getAccountType(),Common.FLOW_TYPE_IN,Common.FLOW_SOURCE_PRODUCT,profit,Common.STATUS_SUCCESS,orderNo);
//        redis.clear(Redis.KEY_USER+sys.getUserNo());
//        log.info("[支付消费]==========>消费处理成功！总成本价格为：{}，利润为：{}，消费金额为：{}，订单编号为：{}",totalCost,profit,money,orderNo);
        return true;
    }

    /**
     * 供应商提现
     *
     * @param accountNo
     * @param money
     * @return
     */
    @Override
    public void SupplierWithdraw(String accountNo, String money) {
        BigDecimal balance = new BigDecimal(money); //提现金额
        if(balance.compareTo(supplierMinAmout) == -1)
            throw new CustomException("很抱歉！最低提现金额为："+supplierMinAmout+"元！");
        UserWallet user = super.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getAccountNo, accountNo));
        if(user.getAvailableBalance().compareTo(balance) == -1)
            throw new CustomException(ExecptionEnum.ERROR_INSUFFICIENT_BALANCE);
        //扣减可用余额
        user.setBalance(user.getBalance().subtract(balance));
        user.setAvailableBalance(user.getAvailableBalance().subtract(balance));
        if(!super.updateById(user))
            throw new CustomException(ExecptionEnum.WITHDRAWAL_FIAL);
        //记录流水
        accFlowService.add("-1",user.getAccountNo(),user.getAccountType(),Common.FLOW_TYPE_OUT,Common.FLOW_SOURCE_WITHDRAW,balance,Common.STATUS_WAIT,"后台审核中，请稍后！");
        log.info("[现金提现]==========>供应商后台提现成功！已送至总后台审核！商家编号为：{},商家账号为：{}, 提现金额w为：{}",user.getUserNo(),accountNo,money);
        redis.clear(Redis.KEY_USER+user.getUserNo());
    }



    /**
     * 获取指定用户编号的账号信息
     * 校验用户是否存在，如果不存则创建
     * @param userNo 用户编号
     */
    @Override
    public UserWallet getUser(String userNo){
        String redisKey = Redis.KEY_USER+userNo;
        String redisItem = userNo;
        if (redis.hexists(redisKey,redisItem))
            return (UserWallet) redis.getHashCache(redisKey,redisItem);
        UserWallet user = super.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getUserNo, userNo));
        if(null == user){ //如果用户不存在
            log.info("[现金消费]========>用户不存在！将创建用户");
            ResponseInfo<UserInfoDTO> userInfoDTOResponseInfo = customerManageFeign.userInfo(Long.valueOf(userNo));
            if(!userInfoDTOResponseInfo.isSuccess())
                throw new CustomException(ExecptionEnum.ERROR_OTHER_SERVICE,userInfoDTOResponseInfo.getCode(),userInfoDTOResponseInfo.getMessage());
            //获取用信息创建用户
            UserInfoDTO u = userInfoDTOResponseInfo.getData();
            if(!this.add(userNo,u.getNickName(),"U01","T01"))
                throw new CustomException(ExecptionEnum.ERROR_CREATE_USER_FAIL);
            user = super.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getUserNo, userNo));
        }
        if(!redis.setHashCache(redisKey,redisItem,user,Redis.EXC_REDIS)){
            log.info("[现金消费]==========>获取用户信息时放入缓存失败！");
        }
        return user;
    }


    /**
     * 新增用户钱包
     *
     * @param userNo
     * @param userName
     * @param userType
     * @param accountType
     * @return ResponseInfo
     */
    @Override
    public boolean add(String userNo, String userName, String userType, String accountType) {
        UserWallet userWallet = new UserWallet();
        userWallet.setUserNo(userNo);
        userWallet.setUserName(userName);
        userWallet.setAccountType(accountType);
        userWallet.setUserType(userType);
        String type = userType.equals(Common.USER_TYPE_SUP) ? "G":"U";
        userWallet.setAccountNo(type+userNo);
        return super.save(userWallet);
    }


    /**
	 * 分页查询
	 *
	 * @param userWallet 请求参数
	 * @return 用户钱包分页列表
	 */
	public IPage<UserWallet> list(UserWallet userWallet,Integer page,Integer pageSize) {
		LambdaQueryWrapper<UserWallet> queryWrapper = new LambdaQueryWrapper<>();
		if (userWallet.getId() != null) {
			queryWrapper.eq(UserWallet::getId, userWallet.getId());
		}
		if (!StringUtils.isEmpty(userWallet.getUserNo())) {
			queryWrapper.eq(UserWallet::getUserNo, userWallet.getUserNo());
		}
		if (!StringUtils.isEmpty(userWallet.getUserName())) {
			queryWrapper.eq(UserWallet::getUserName, userWallet.getUserName());
		}
		if (!StringUtils.isEmpty(userWallet.getAccountNo())) {
			queryWrapper.eq(UserWallet::getAccountNo, userWallet.getAccountNo());
		}
		if (!StringUtils.isEmpty(userWallet.getAccountType())) {
			queryWrapper.eq(UserWallet::getAccountType, userWallet.getAccountType());
		}
		if (!StringUtils.isEmpty(userWallet.getUserType())) {
			queryWrapper.eq(UserWallet::getUserType, userWallet.getUserType());
		}
		if (userWallet.getBalance() != null) {
			queryWrapper.eq(UserWallet::getBalance, userWallet.getBalance());
		}
		if (userWallet.getFrozenBalance() != null) {
			queryWrapper.eq(UserWallet::getFrozenBalance, userWallet.getFrozenBalance());
		}
		if (userWallet.getAvailableBalance() != null) {
			queryWrapper.eq(UserWallet::getAvailableBalance, userWallet.getAvailableBalance());
		}
		if (!StringUtils.isEmpty(userWallet.getBusinessStatus())) {
			queryWrapper.eq(UserWallet::getBusinessStatus, userWallet.getBusinessStatus());
		}
		if (userWallet.getEnable() != null) {
			queryWrapper.eq(UserWallet::getEnable, userWallet.getEnable());
		}
		if (userWallet.getCreateTime() != null) {
			queryWrapper.eq(UserWallet::getCreateTime, userWallet.getCreateTime());
		}
		if (userWallet.getLastModifyTime() != null) {
			queryWrapper.eq(UserWallet::getLastModifyTime, userWallet.getLastModifyTime());
		}
		queryWrapper.orderByDesc(UserWallet::getId);
		Page<UserWallet> pages = new Page<UserWallet>(page == null ? 1 : page, pageSize == null ? 15 : pageSize);
		return super.baseMapper.selectPage(pages, queryWrapper);
	}



}
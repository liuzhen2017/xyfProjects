package com.xinyunfu.service.impl;

import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.constant.Redis;
import com.xinyunfu.dto.UserInfoDTO;
import com.xinyunfu.entity.UserWallet;
import com.xinyunfu.feign.CustomerManageFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.UserWalletService;
import com.xinyunfu.util.RedisCommonUtil;
import com.xinyunfu.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.entity.WalletAccFlow;
import com.xinyunfu.mapper.AccFlowMapper;
import com.xinyunfu.service.AccFlowService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author liuzhen
 * 每笔转账的转入转出记录 Service
 */
@Slf4j
@Service
public class AccFlowServiceImpl extends ServiceImpl<AccFlowMapper, WalletAccFlow> implements AccFlowService{


    @Autowired
    private UserWalletService userWalletService;
    @Autowired
    private CustomerManageFeign customerManageFeign;
    @Autowired
    private RedisCommonUtil redis;

    /**
     * 记录流水
     *
     * @param orderNo     主订单编号（无可可-1）
     * @param accountNo   账户编号
     * @param accountType 账户类型    T00积分钱包,T01现金钱包,T02优惠券钱包
     * @param flowType    流水类型    in转入，out转出
     * @param flowSource  流水来源    商品/套餐购买 product, 用户转账 transfer
     * @param amount      流水金额
     * @param status      流水状态    wait等待支付，success支付成功，failed支付失败
     * @param desc        流水描述
     * @return
     */
    @Override
    public boolean add(String orderNo, String accountNo, String accountType, String flowType, String flowSource, BigDecimal amount, String status,String desc) {
        return super.save(new WalletAccFlow(orderNo,accountNo,accountType,flowType,flowSource,amount,status,desc));
    }

    /**
     * 获取我的记录
     *
     * @param userNo   用户编号
     * @param page     当前页数
     * @param pageSize 没页大小
     * @param type     数据类型 （in/out）
     * @return
     */
    @Override
    public IPage<WalletAccFlow> getMyRecord(String userNo, Integer page, Integer pageSize, String type) {
        String redisKey = Redis.KEY_USER+userNo;
        String redisItem = type+"_"+page+"_"+pageSize;
        if(redis.hexists(redisKey,redisItem))
            return (IPage<WalletAccFlow>) redis.getHashCache(redisKey,redisItem);
        UserWallet one = userWalletService.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getUserNo, userNo));
        if(null == one)
            throw new CustomException(ExecptionEnum.GET_USER_INFO_FAIL);
        IPage<WalletAccFlow> iPage = super.page(new Page<>(page, pageSize), new LambdaQueryWrapper<WalletAccFlow>()
                .eq(WalletAccFlow::getAccountNo, one.getAccountNo())
                .eq(WalletAccFlow::getFlowType, type)
                .orderByDesc(WalletAccFlow::getId));
        iPage.getRecords().forEach( wall -> {
            wall.setShowDate(TimeUtils.getStr(wall.getCreateTime()));
        });
        if(!redis.setHashCache(redisKey,redisItem,iPage,Redis.EXC_REDIS)){
            log.info("[获取记录]=========>获取我的记录时放入缓存失败！");
        }
        return iPage;
    }

    /**
     * 供应商账户余额提现审批
     * @param id   流水号
     * @param type 是否同意审批 （0同意/1拒绝）
     * @param message 驳回消息
     * @return
     */
    @Override
    @Transactional
    public void withdrawApproval(String id, Integer type,String message) {
        //获取流水信息
        WalletAccFlow flow = super.getById(id);
        UserWallet user = userWalletService.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getAccountNo,flow.getAccountNo()));
        if(type == 0){ //同意 转入系统账户
            UserWallet sys = userWalletService.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getAccountNo, Common.SYSTEM_ACCOUNT));
            sys.setBalance(sys.getBalance().add(flow.getAmount()));
            sys.setAvailableBalance(sys.getAvailableBalance().add(flow.getAmount()));
            this.add("-1",sys.getAccountNo(),sys.getAccountType(),Common.FLOW_TYPE_IN,Common.FLOW_SOURCE_WITHDRAW,flow.getAmount(),Common.STATUS_SUCCESS,"提现入账");
            if(!userWalletService.updateById(sys))
                throw new CustomException(ExecptionEnum.ERROR_FAIL);
            //修改流水状态
            flow.setStatus(Common.STATUS_SUCCESS);
            flow.setFlowDesc("后台提现，已处理完成！");
            redis.clear(Redis.KEY_USER+sys.getUserNo());
        }else{ //拒绝 回退至对应账户
            user.setBalance(user.getBalance().add(flow.getAmount()));
            user.setAvailableBalance(user.getAvailableBalance().add(flow.getAmount()));
            this.add("-1",user.getAccountNo(),user.getAccountType(),Common.FLOW_TYPE_IN,Common.FLOW_SOURCE_WITHDRAW,flow.getAmount(),Common.STATUS_SUCCESS,"提现回退");
            if(!userWalletService.updateById(user))
                throw new CustomException(ExecptionEnum.ERROR_FAIL);
            //修改流水状态
            flow.setStatus(Common.STATUS_FAILED);
            flow.setFlowDesc("提现驳回："+message);
        }
        //保存流水信息
        if(!super.updateById(flow))
            throw new CustomException(ExecptionEnum.ERROR_FAIL);
        redis.clear(Redis.KEY_USER+user.getUserNo());
    }


    /**
     * 分页查询
     * @param walletAccFlow 请求参数
     * @return 每笔转账的转入转出记录分页列表
     */
    public IPage<WalletAccFlow> list(WalletAccFlow walletAccFlow, Integer page, Integer pageSize) {
        LambdaQueryWrapper<WalletAccFlow> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(walletAccFlow.getOrderNo())) {
            queryWrapper.eq(WalletAccFlow::getOrderNo, walletAccFlow.getOrderNo());
        }
        if (!StringUtils.isEmpty(walletAccFlow.getAccountNo())) {
            queryWrapper.eq(WalletAccFlow::getAccountNo, walletAccFlow.getAccountNo());
        }
        if (!StringUtils.isEmpty(walletAccFlow.getAccountType())) {
            queryWrapper.eq(WalletAccFlow::getAccountType, walletAccFlow.getAccountType());
        }
        if (!StringUtils.isEmpty(walletAccFlow.getFlowType())) {
            queryWrapper.eq(WalletAccFlow::getFlowType, walletAccFlow.getFlowType());
        }
        if (!StringUtils.isEmpty(walletAccFlow.getFlowSource())) {
            queryWrapper.eq(WalletAccFlow::getFlowSource, walletAccFlow.getFlowSource());
        }
        if (walletAccFlow.getAmount() != null) {
            queryWrapper.eq(WalletAccFlow::getAmount, walletAccFlow.getAmount());
        }
        if (!StringUtils.isEmpty(walletAccFlow.getStatus())) {
            queryWrapper.eq(WalletAccFlow::getStatus, walletAccFlow.getStatus());
        }
        if (StringUtils.isNotEmpty(walletAccFlow.getShowDate()) && !walletAccFlow.getShowDate().equals(";")) {
            String[] split = walletAccFlow.getShowDate().split(";");
            queryWrapper.ge(WalletAccFlow::getCreateTime,split[0]);
            queryWrapper.le(WalletAccFlow::getCreateTime,split[1]);
        }
        queryWrapper.orderByDesc(WalletAccFlow::getId);
        IPage<WalletAccFlow> selectPage = super.baseMapper.selectPage(new Page(page,pageSize), queryWrapper);
        return selectPage;
    }

}
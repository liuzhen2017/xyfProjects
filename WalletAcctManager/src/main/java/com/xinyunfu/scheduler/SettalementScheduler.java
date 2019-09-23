package com.xinyunfu.scheduler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.constant.PayType;
import com.xinyunfu.constant.Redis;
import com.xinyunfu.dto.order.OrderItem;
import com.xinyunfu.dto.order.OrderMaster;
import com.xinyunfu.entity.UserWallet;
import com.xinyunfu.feign.OrderManageFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.UserWalletService;
import com.xinyunfu.service.impl.AccFlowServiceImpl;
import com.xinyunfu.util.RedisCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author XRZ
 * @date 2019-09-04
 * @Description : 定时任务 定时 给商家结算
 *
 */
@Component
@Slf4j
public class SettalementScheduler {

    /**
     * 间隔时间 2小时
     */
    static final long TIME = 1000*60*60*2;
    @Autowired
    private RedisCommonUtil redis;
    @Autowired
    private AccFlowServiceImpl accFlowService;
    @Autowired
    private OrderManageFeign orderManageFeign;
    @Autowired
    private UserWalletService userWalletService;

    /**
     *  商家结算
     */
    @Scheduled(fixedDelay = TIME)
    public void settalement() {
        //获取所有待结算的订单信息
        ResponseInfo<List<OrderItem>> res = orderManageFeign.getOrderInfo("a");
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.ERROR_GET_ORDER_INFO);
        BigDecimal profit = new BigDecimal(0); //记录总利润
        int count = 0;
        //遍历订单列表
        for (OrderItem item : res.getData()) {
            try {
                profit = hendingOrder(item);
                count++;
            }catch (CustomException e){
                log.error("[定时结算]==========>处理订单发现异常，异常信息：{}，已跳过！订单号为：{}",e.getEe().getMessage(),item.getItemNo());
            }
        }
        //给内部账户转账
        if(profit.compareTo(new BigDecimal(0)) != 0){
            UserWallet sys = userWalletService.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getAccountNo, Common.INNER_ACCOUNT));
            sys.setBalance(sys.getBalance().add(profit));
            sys.setAvailableBalance(sys.getAvailableBalance().add(profit));
            if(!userWalletService.updateById(sys))
                throw new CustomException(ExecptionEnum.SYSTEM_ACCOUNT_ENTRY);
            accFlowService.add("-2",sys.getAccountNo(),sys.getAccountType(),Common.FLOW_TYPE_IN,Common.FLOW_SOURCE_PRODUCT,profit,Common.STATUS_SUCCESS,"商家结算");
            redis.clear(Redis.KEY_USER+sys.getUserNo());
        }
        log.info("[定时结算]==========>处理完成！成功处理订单数量为：{},利润金额为：{}",count,profit);
    }


    /**
     * 处理订单
     * @param item 订单对象
     * @return 返回 该笔订单 利润
     */
    @Transactional
    public BigDecimal hendingOrder(OrderItem item){
        //判断支付方式
        if (item.getPayType() == PayType.CASH_WALLET) { //如果支付方式为现金支付
            UserWallet user = userWalletService.getUser(item.getUserId());
            if (item.getPayAmount().compareTo(user.getFrozenBalance()) == 1){ //如果该笔订单的支付金额 大于 冻结金额 则跳过该订单
                log.error("[定时结算]==========>该笔订单 的支付金额 大于 该用户的钱包中的 冻结金额，已跳过！用户编号为：{}，订单编号为：{}，支付金额为：{}，账户编号为：{}，冻结金额为：{}",user.getUserNo(),item.getPayAmount(),user.getAccountNo(),user.getFrozenBalance());
                throw new CustomException(ExecptionEnum.VALUE_CHECK_FAILED);
            }
            user.setFrozenBalance(user.getFrozenBalance().subtract(item.getPayAmount())); //扣除冻结金额
            if(!userWalletService.updateById(user))
                throw new CustomException(ExecptionEnum.TIMING_CHANGE_ORDER_FAIL);
            redis.clear(Redis.KEY_USER+user.getUserNo());
            log.info("[定时结算]==========>支付方式为现金消费,已成功扣除用户冻结金额!");
        }
        //给商家账户转账
        Long storeId = item.getStoreId();//获取商家ID 即商家账户的用户编号
        BigDecimal cost = item.getCostAmount().add(item.getTotalFreight());       //子订单的总成本价格
        UserWallet store = userWalletService.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getUserNo, storeId));
        store.setBalance(store.getBalance().add(cost));
        store.setAvailableBalance(store.getAvailableBalance().add(cost));
        if(!userWalletService.updateById(store))
            throw new CustomException(ExecptionEnum.TIMING_CHANGE_ORDER_FAIL);
        accFlowService.add(item.getItemNo(),store.getAccountNo(),store.getAccountType(), Common.FLOW_TYPE_IN,Common.FLOW_SOURCE_PRODUCT,cost,Common.STATUS_SUCCESS,item.getItemNo());
        redis.clear(Redis.KEY_USER+store.getUserNo());
        log.info("[定时结算]==========>给商家转账成功！商家编号：{}，订单编号：{}，转入金额：{}",storeId,item.getItemNo(),cost);
        //修改订单 的结算状态为 已结算
        ResponseInfo<Object> objectResponseInfo = orderManageFeign.settlementOrder(item.getItemNo());
        if(!objectResponseInfo.isSuccess())
            throw new CustomException(ExecptionEnum.TIMING_CHANGE_ORDER_STATUS_FAIL);
        return item.getPayAmount().subtract(cost); // 订单支付金额 - 订单的总成本价格
    }


}

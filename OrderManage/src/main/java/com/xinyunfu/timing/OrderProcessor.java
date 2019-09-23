package com.xinyunfu.timing;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.*;
import com.xinyunfu.feign.ProductManageFeign;
import com.xinyunfu.feign.VolumeMarketFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.OrderCommodity;
import com.xinyunfu.model.OrderItem;
import com.xinyunfu.service.IOrderCommodityService;
import com.xinyunfu.service.IOrderItemService;
import com.xinyunfu.service.IOrderQancelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XRZ
 * @date 2019/7/13
 * @Description : 订单处理器
 */
@Slf4j
@Component
public class OrderProcessor {

    @Autowired
    private IOrderQancelService iOrderQancelService;
    @Autowired
    private VolumeMarketFeign volumeMarketFeign;
    @Autowired
    private IOrderItemService iOrderItemService;


    /**
     * 处理未支付的订单
     * @param content
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MQ.ORDER_CANCEL_QUEUE,durable = "true"),
            exchange = @Exchange(name = MQ.ORDER_EXCHANGE,type = ExchangeTypes.DIRECT,
            arguments = @Argument(name="x-delayed-type",value="direct"),delayed=Exchange.TRUE),key = MQ.KEY_CANCEL))
    public void update(String content){
        String[] strs = content.split("_");
        String currentUserId = strs[0];
        String orderNo = strs[1];
        //取消订单
        log.info("[订单服务]===========>定时任务: 处理未支付的订单,订单号为：{} 的订单已超时，将自动取消!",orderNo);
        iOrderQancelService.cancelOrder(currentUserId,orderNo, QancelType.AUTO);
    }

    /**
     * 处理已取消超过七天的订单
     * @param content
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MQ.ORDER_DELETE_QUEUE,durable = "true"),
            exchange = @Exchange(name = MQ.ORDER_EXCHANGE,type = ExchangeTypes.DIRECT,
            arguments = @Argument(name="x-delayed-type",value="direct"),delayed=Exchange.TRUE), key = MQ.KEY_DELETE))
    public void delete(String content){
        String[] strs = content.split("_");
        String currentUserId = strs[0];
        String orderNo = strs[1];
        //删除订单  设置该子订单数据状态为禁用，支付状态为交易关闭
        if(!iOrderItemService.delOrder(currentUserId,orderNo))
            throw new CustomException(ExecptionEnum.ERROR_TIMING_DELETE_ORDER);
        log.info("[订单服务]===========>定时任务:处理已取消超过七天的订单,订单号为：{} 的订单已自动删除！",orderNo);
    }

    /**
     * 处理自动确认收货的订单
     * @param content
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MQ.ORDER_AUTO_QUEUE,durable = "true"),
            exchange = @Exchange(name = MQ.ORDER_EXCHANGE,type = ExchangeTypes.DIRECT,
            arguments = @Argument(name="x-delayed-type",value="direct"),delayed=Exchange.TRUE), key = MQ.KEY_AUTO))
    public void confirm(String content){
        String[] strs = content.split("_");
        String currentUserId = strs[0];
        String orderNo = strs[1];
        //修改订单状态为 待评价
        if(!iOrderItemService.updateStatus(currentUserId,orderNo,IStatus.NOT_COMMENT))
            throw new CustomException(ExecptionEnum.ERROR_TIMING_CONFIRM_ORDER);
        log.info("[订单服务]===========>定时任务:处理自动确认收货的订单,订单号为：{} 的订单已自动签收！",orderNo);
    }

//    /**
//     * 延迟 释放券 （防止用户恶意刷券）
//     * @param content
//     */
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = MQ.ORDER_COUPON_QUEUE,durable = "true"),
//            exchange = @Exchange(name = MQ.ORDER_EXCHANGE,type = ExchangeTypes.DIRECT,
//            arguments = @Argument(name="x-delayed-type",value="direct"),delayed=Exchange.TRUE), key = MQ.KEY_COUPON))
//    public void callback(String content){
//        String[] strs = content.split("_");
//        String currentUserId = strs[0];
//        String couIds = strs[1];        //券ID 多个使用分号拼接
//        ResponseInfo<String> res = volumeMarketFeign.updateCoupon(couIds, currentUserId, CouponStatus.EFFECTIVE);
//        if(!res.isSuccess())
//            throw new CustomException(ExecptionEnum.ERROR_TIMING_RETURN_VOUCHERS);
//        log.info("[订单服务]=========>定时任务:取消订单，调用券集市释放券成功！券的ID为：{}",couIds);
//    }



}

package com.xinyunfu.constant;

/**
 * @author XRZ
 * @date 2019/7/15
 * @Description : MQ处理消息常量
 */
public interface MQ {


    /**
     * 订单交换机
     */
    String ORDER_EXCHANGE = "othreOrder.exchange";

    /**
     * 取消订单的键
     */
    String KEY_CANCEL = "cancel";
    /**
     * 取消订单队列
     */
    String ORDER_CANCEL_QUEUE = "othreOrder.cancel.queue";

    /**
     * 删除订单的键
     */
    String KEY_DELETE = "delete";
    /**
     * 删除订单队列
     */
    String ORDER_DELETE_QUEUE = "othreOrder.delete.queue";

    /**
     * 确认收货订单的键
     */
    String KEY_AUTO = "auto";
    /**
     * 确认收货订单交队列
     */
    String ORDER_AUTO_QUEUE = "othreOrder.auto.queue";

    /**
     * 延迟释放券的键
     */
    String KEY_COUPON = "coupon";

    /**
     * 延迟释放券的队列
     */
    String ORDER_COUPON_QUEUE = "othreOrder.coupon.queue";







}

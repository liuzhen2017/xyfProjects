package com.xinyunfu.constant;

/**
 * @author XRZ
 * @date 2019/7/11
 * @Description : Redis 存取的key前缀
 */
public interface Redis{

    /**
     * 七天
     * 单位：秒
     */
    long EXC_REDIS = 3600 * 24 * 7;


    /**
     * 订单对象     （七天）
     */
    String KEY_ORDER = "order_";

    /**
     * 购物车对象   （七天）
     */
    String KEY_ORDERCART = "orderCart_";

    /**
     * 价格清单对象 （七天）
     */
    String KEY_ORDERPRICES = "orderPrices_";

    /**
     * 收货信息对象 （七天）
     */
    String KEY_ORDERCONSIGNEE = "orderConsignee_";

    /**
     * 发票对象 (7天)
     */
    String KEY_INVOICE = "orderInvoice_";

    /**
     * 优惠券对象
     */
    String KEY_COUPONS = "orderCoupon_";

    /**
     * 记录购买套餐的KEY
     */
    String KEY_SET_MEAL = "setMeal_";

    /**
     * 物流公司信息的KEY
     */
    String KEY_EXPRESS = "Express_";

    /**
     * 时间限制的KEY
     */
    String KEY_TIME_OUT = "timeOut_";

}

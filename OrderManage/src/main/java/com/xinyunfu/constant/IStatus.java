package com.xinyunfu.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XRZ
 * @date 2019/7/13
 * @Description : 子订单状态相关常量
 */
public interface IStatus {

    /**
     * 全部
     */
    int ALL = -1;

    /**
     * 待支付
     */
    int UNPAID = 0;

    /**
     * 待发货
     */
    int UNSHIPPED = 1;

    /**
     * 待签收
     */
    int UNSIGEND = 2;

    /**
     * 待评价
     */
    int NOT_COMMENT = 4;

    /**
     * 维权中
     */
    int PROTECTING_RIGHTS = 5;

    /**
     * 预留中
     */
    int RESSERVED = 6;

    /**
     * 交易完成
     */
    int COMPLETE = 8;

    /**
     * 已取消
     */
    int QANCEL = 9;

    /**
     * 订单异常
     */
    int ERROR = 10;

    /**
     * 已退款
     */
    int REFUNDED = 7;

    /**
     *  已换货
     */
    int EXCHANGE = 11;

    /**
     * 已退货换货
     */
    int RETURN_REFUND = 12;

    String[] STATUS = {"待付款","待发货","待收货","已签收","待评价","维权中","预留中","已退款","交易完成","已取消","订单异常,请联系客服！","已换货","已退货换货"};

}

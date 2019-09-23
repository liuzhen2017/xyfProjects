package com.xinyunfu.constant;

/**
 * @author XRZ
 * @date 2019/7/17
 * @Description : 主订单状态相关常量
 */
public interface MStatus {

    /**
     * 待支付
     */
    int UNPAID = 0;

    /**
     * 支付成功
     */
    int PAY_SUCCESS = 1;

    /**
     * 支付失败
     */
    int PAY_FAIL = 2;

    /**
     * 支付完成
     */
    int DEAL_CLOSURE = 3;
}

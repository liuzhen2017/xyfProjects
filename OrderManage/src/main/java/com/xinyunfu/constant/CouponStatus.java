package com.xinyunfu.constant;

/**
 * @author XRZ
 * @date 2019/7/24
 * @Description : 优惠券的状态
 */
public interface CouponStatus {

    /**
     * 未使用
     */
    String EFFECTIVE = "00";

    /**
     * 已使用
     */
    String FAILURE = "01";

    /**
     * 锁定中
     */
    String LOCK = "08";
}

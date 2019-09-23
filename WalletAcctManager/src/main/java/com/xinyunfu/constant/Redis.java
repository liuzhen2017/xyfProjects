package com.xinyunfu.constant;

/**
 * @author XRZ
 * @date 2019/8/22
 * @Description : redis 相关KEY
 */
public interface Redis {


    /**
     * 七天
     * 单位：秒
     */
    long EXC_REDIS = 3600 * 24 * 7;

    /**
     * 账户信息的KEY
     */
    String KEY_USER = "userWallet_";

}

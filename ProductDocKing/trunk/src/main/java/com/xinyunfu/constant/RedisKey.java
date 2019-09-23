package com.xinyunfu.constant;

/**
 * @author XRZ
 * @date 2019/7/11
 * @Description : Redis 存取的key
 */
public interface RedisKey {

    /**
     * 5分钟
     * 单位：秒
     */
    long EXC_REDIS = 300;


    /**
     * token
     */
    String KEY_Token = "currentToken";

}

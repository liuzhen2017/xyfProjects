package com.xinyunfu.service.redis;

/**
 * redis中key的管理<br/>
 * 数据发生更改，redis缓存失效，不主动往redis写数据
 */
public class RedisKeyRules {

    private static String base = "infocenter:";

    /** 推送设备信息 */
    public static String pushDeviceMessage(Long userNo){ return base + "push:device:user:no:" + userNo; }

    /** 推送模板信息 */
    public static String pushTemplate(String templateName){ return base + "push:template:info:name:" + templateName; }

}

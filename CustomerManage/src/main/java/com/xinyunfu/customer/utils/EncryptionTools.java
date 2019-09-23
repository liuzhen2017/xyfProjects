package com.xinyunfu.customer.utils;

import org.springframework.util.DigestUtils;

/**
 * 加密工具类
 */
public class EncryptionTools {

    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
    public static String md5(String str, String salt){
        return md5(str + salt);
    }
}

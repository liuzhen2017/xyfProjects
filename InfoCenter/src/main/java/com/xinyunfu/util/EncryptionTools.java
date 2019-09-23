package com.xinyunfu.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密工具类
 */
public class EncryptionTools {

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }
    public static String md5(String str, String salt){
        return md5(str + salt);
    }
}

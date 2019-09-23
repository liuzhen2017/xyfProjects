package com.gateway.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.alibaba.fastjson.JSONObject;

public class MD5Utils {

	
	
	 public static String md5(String plainText) {
	        byte[] secretBytes = null;
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(plainText.getBytes());
	            secretBytes = md.digest();
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException("md5加密异常");
	        }
	        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
	        md5code = "0000000".concat(md5code);
	        return md5code.substring(md5code.length()-32);
	 }
	 
}

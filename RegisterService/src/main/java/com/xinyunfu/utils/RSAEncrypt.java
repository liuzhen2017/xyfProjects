package com.xinyunfu.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAEncrypt {
	
	private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  
	
	/** 
	 * 随机生成密钥对 
	 * @throws NoSuchAlgorithmException 
	 */  
	 /**
     * 加密算法RSA
     */
    private static final String KEY_ALGORITHM = "RSA";
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 常量0
     */
    private static final int ZERO = 0;
    /**
     * RSA最大加密明文最大大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     *  RSA最大解密密文最大大小
     * 	当密钥位数为1024时,解密密文最大是 128
     * 	当密钥位数为2048时需要改为 256 不然会报错（Decryption error）
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * 默认key大小
     */
    private static final int DEFAULT_KEY_SIZE = 1024;

    /**
     * 生成密钥对(公钥和私钥)
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        return initKey(DEFAULT_KEY_SIZE);
    }

    /**
     * 生成密钥对(公钥和私钥)
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey(int keySize) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(keySize);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static void main1(String[] args) throws Exception {
        Map<String, Object> stringObjectMap = initKey(2048);

        RSAPublicKey publicKey = (RSAPublicKey) stringObjectMap.get(PUBLIC_KEY);
        RSAPrivateKey privateKey = (RSAPrivateKey) stringObjectMap.get(PRIVATE_KEY);

        String pubKey = Base64.encodeBase64String(publicKey.getEncoded());
        String priKey = Base64.encodeBase64String(privateKey.getEncoded());

        System.out.println("公钥："+pubKey);
        System.out.println("私钥："+priKey);


    }


    /**
     * 公钥加密
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return encrypt(data, KeyFactory.getInstance(KEY_ALGORITHM), keyFactory.generatePublic(x509KeySpec));
    }




    /**
     * 	私钥解密
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return decrypt(encryptedData, keyFactory, keyFactory.generatePrivate(pkcs8KeySpec));
    }


 

 
    /**
     * 私钥解密
     * @param encryString 密文String
     * @param privateKey  私钥
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unused")
	public static String decryptString(String encryString,String privateKey) throws Exception {
    	byte[] bytes = Base64.decodeBase64(encryString);
    	return new String(decryptByPrivateKey(bytes, privateKey));
    }

    /**
     *	 解密公共方法
     */
    private static byte[] decrypt(byte[] data, KeyFactory keyFactory, Key key) throws Exception {

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        return encryptAndDecrypt(data, cipher, MAX_DECRYPT_BLOCK);
    }

    /**
     * 加密公共方法
     */
    private static byte[] encrypt(byte[] data, KeyFactory keyFactory, Key key) throws Exception {
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return encryptAndDecrypt(data, cipher, MAX_ENCRYPT_BLOCK);
    }


    /**
     * 加密解密分段处理公共方法
     */
    private static byte[] encryptAndDecrypt(byte[] data, Cipher cipher, int maxSize) throws Exception {
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = ZERO;
        byte[] cache;
        int i = ZERO;
        // 对数据分段加密
        while (inputLen - offSet > ZERO) {
            if (inputLen - offSet > maxSize) {
                cache = cipher.doFinal(data, offSet, maxSize);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, ZERO, cache.length);
            i++;
            offSet = i * maxSize;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
    
    
    
    /**
     *	版本检查
     * @param currentVersion
     * @param appVersion
     * @return
     */
    public static boolean checkVersion(String currentVersion,String appVersion) {
    	
    	return appVersion.startsWith(currentVersion.substring(0,currentVersion.indexOf(".")));
    }
    
   
}

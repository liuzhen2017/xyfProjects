package com.xingyunfu.ebank.util;

import org.bouncycastle.util.encoders.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RSAUtil {

  public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
  /**
   * RSA最大加密明文大小
   */
  private static final int MAX_ENCRYPT_BLOCK = 117;

  /**
   * RSA最大解密密文大小
   */
  private static final int MAX_DECRYPT_BLOCK = 128;

  /**
   * RSA签名
   * 
   * @param content 待签名数据
   * @param privateKey 商户私钥
   * @param input_charset 编码格式
   * @return 签名值
   */
  public static String sign(String content, String privateKey, String input_charset) {
    try {
      PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
      KeyFactory keyf = KeyFactory.getInstance("RSA");
      PrivateKey priKey = keyf.generatePrivate(priPKCS8);

      java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

      signature.initSign(priKey);
      signature.update(content.getBytes(input_charset));

      byte[] signed = signature.sign();

      return new BASE64Encoder().encode(signed);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * RSA验签名检查
   * 
   * @param content 待签名数据
   * @param sign 签名值
   * @param paf_public_key 平安付公钥
   * @param input_charset 编码格式
   * @return 布尔值
   */
  public static boolean verify(String content, String sign, String paf_public_key, String input_charset) {

    try {
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      byte[] encodedKey = Base64.decode(paf_public_key);
      PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


      java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

      signature.initVerify(pubKey);
      signature.update(content.getBytes(input_charset));

      boolean bverify = signature.verify(Base64.decode(sign));
      return bverify;

    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * 解密
   * 
   * @param content 密文
   * @param private_key 商户私钥
   * @param input_charset 编码格式
   * @return 解密后的字符串
   */
  public static String decrypt(String content, String private_key, String input_charset) throws Exception {
    PrivateKey prikey = getPrivateKey(private_key);

    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, prikey);

    InputStream ins = new ByteArrayInputStream(Base64.decode(content));
    ByteArrayOutputStream writer = new ByteArrayOutputStream();
    // rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
    byte[] buf = new byte[128];
    int bufl;

    while ((bufl = ins.read(buf)) != -1) {
      byte[] block = null;

      if (buf.length == bufl) {
        block = buf;
      } else {
        block = new byte[bufl];
        for (int i = 0; i < bufl; i++) {
          block[i] = buf[i];
        }
      }

      writer.write(cipher.doFinal(block));
    }

    return new String(writer.toByteArray(), input_charset);
  }


  /**
   * 得到私钥
   * 
   * @param key 密钥字符串（经过base64编码）
   * @throws Exception
   */
  public static PrivateKey getPrivateKey(String key) throws Exception {

    byte[] keyBytes;

    keyBytes = Base64.decode(key);

    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

    return privateKey;
  }

  /**
   * 得到公钥
   *
   * @param key 密钥字符串（经过base64编码）
   * @throws Exception
   */
  public static PublicKey getPublicKey(String key) throws Exception {
    byte[] keyBytes;
    keyBytes = (new BASE64Decoder()).decodeBuffer(key);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PublicKey publicKey = keyFactory.generatePublic(keySpec);
    return publicKey;
  }

  /**
   * 使用公钥对明文进行加密，返回BASE64编码的字符串
   * 
   * @param publicKey
   * @param plainText
   * @return
   * @throws Exception
   */
  /**
   * <p>
   * 公钥加密
   * </p>
   *
   * @param data 源数据
   * @param publicKey 公钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static String encryptByPublicKey(byte[] data, String publicKey) throws Exception {
    byte[] keyBytes = Base64.decode(publicKey);
    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key publicK = keyFactory.generatePublic(x509KeySpec);
    // 对数据加密
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(Cipher.ENCRYPT_MODE, publicK);
    int inputLen = data.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;
    byte[] cache;
    int i = 0;
    // 对数据分段加密
    while (inputLen - offSet > 0) {
      if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
        cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
      } else {
        cache = cipher.doFinal(data, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * MAX_ENCRYPT_BLOCK;
    }
    byte[] encryptedData = out.toByteArray();
    out.close();
    // return encryptedData
    return (new BASE64Encoder()).encode(encryptedData);

  }



}

package com.gateway.utils;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtUtils {

	

	 /**
	 * @param userNo        用户id
	 * @param encryVersion  加密版本号
	 * @param seconds       token有效时间，秒
	 * @return
	 */
	public static String getToken(String userNo,int hours,String encryVersion) {
	        Date start = new Date();
	        long currentTime = System.currentTimeMillis() + hours * 1000 * 3600;
	        Date end = new Date(currentTime);
	        String token = JWT.create().withAudience(userNo).withIssuedAt(start).withExpiresAt(end)
	                .sign(Algorithm.HMAC256(encryVersion));
	        return token;
	 }
	   
	   
	 public static String getUserNoByToken(String token) throws Exception {
		   if(StringUtils.isBlank(token)) {
			   throw new Exception("您未登录，请重新登录");
		   }
		   return  JWT.decode(token).getAudience().get(0);
	 }
	
	 
	 public static boolean checkToken(String token,String encryVersion) throws BusinessException{
		 boolean bool = false;
		 try {
			 JWTVerifier decryer = JWT.require(Algorithm.HMAC256(encryVersion)).build();
			 decryer.verify(token);
			 bool = true;
		 } catch (Exception e) {
		 	 throw new BusinessException("token解密失败");
		 }
		 return bool;
	 }
	 
}

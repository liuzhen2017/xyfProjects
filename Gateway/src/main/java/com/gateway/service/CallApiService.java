package com.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.gateway.sao.CustomerManageSao;
import com.gateway.utils.JwtUtils;
import com.gateway.utils.RSAEncrypt;
import com.gateway.utils.ResponseInfo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CallApiService {

	
	
	@Autowired
	private CustomerManageSao sao;
	
	@Autowired
	private RedisService redis;
	
	@Value("${login.token.timeout}")
	private String hours;
	
	@Value("${xyf.api.version}")
	private String apiVersion;
	
	@Value("${handler.encry.key}")
	private String encryKey;
	
	@Value("${auth.isOn.takeOn}")
    private String isOn;	
	
	/**
	 *	登录接口
	 * @param json
	 * @param loginType
	 * @return
	 * @throws Exception 
	 */
	public JSONObject login(JSONObject json,String realIp) throws Exception{
		String data = json.getString("data");
		if("start".equals(isOn)) {//  加密开了，需要去解
			data = RSAEncrypt.decryptString(data, encryKey);
		}
		String loginResult = sao.login(JSONObject.parseObject(data),realIp);
		log.info("login by loginResult{}", loginResult);
	    JSONObject res = JSONObject.parseObject(loginResult);
	    res.put("apiVersion", apiVersion);
	    if(ResponseInfo.CODE_SUCCESS.equals(res.getString("code"))) {//  登录成功
	      JSONObject resultData = JSONObject.parseObject(res.getString("data"));
	      String token = JwtUtils.getToken(""+resultData.getString("userNo"), Integer.parseInt(hours), apiVersion);
	      resultData.put("token", token);
	      redis.set("gateway"+resultData.getString("userNo"), token,10*365*24*3600*1000L);
          res.put("data", resultData);
	      return res;
	    }
		return res;
	}
	
	
	public JSONObject resgister(JSONObject json,String realIp) throws Exception {
		String data = json.getString("data");
		if("start".equals(isOn)) {//  加密开了，需要去解
			data = RSAEncrypt.decryptString(data, encryKey);
		}
		String resgisterResult = sao.resgister(JSONObject.parseObject(data), realIp);
		log.info("resgisterResult ==> {}",resgisterResult);
		JSONObject res = JSONObject.parseObject(resgisterResult);
		res.put("apiVersion", apiVersion);
		if(ResponseInfo.CODE_SUCCESS.equals(res.getString("code"))) {
			 JSONObject resultData = JSONObject.parseObject(res.getString("data"));
			 String token = JwtUtils.getToken(""+resultData.getLong("userNo"), Integer.parseInt(hours), apiVersion);
			 resultData.put("token", token);
			 redis.set("gateway"+resultData.getString("userNo"), token,10*365*24*3600*1000L);
	         res.put("data", resultData);
			 return res;
		}
		return res;
	}
	
}

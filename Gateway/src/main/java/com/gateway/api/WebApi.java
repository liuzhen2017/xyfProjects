package com.gateway.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gateway.service.CallApiService;
import com.gateway.utils.ResponseInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jace
 *
 */
@RestController
@Slf4j
public class WebApi {


	
	@Autowired
	private CallApiService service;
	
	
	@PostMapping("/api/login")
	public JSONObject login(@RequestBody JSONObject json,@RequestHeader("X-Real-IP")String realIp) {
		log.info("login===={}",json);
		try {
			return service.login(json,realIp);
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject res = new JSONObject();
			res.put("code", "9999");
			res.put("message",e.getMessage());
			return res;
		}
	}
	
	
	
	@PostMapping("/api/register")
	public JSONObject resgister(@RequestBody JSONObject json,@RequestHeader("X-Real-IP")String realIp)  {
		log.info("resgister ==>{}",json);
		try {
			return service.resgister(json,realIp);
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject res = new JSONObject();
			res.put("code", "9999");
			res.put("message",e.getMessage());
			return res;
		}
	}
	
}

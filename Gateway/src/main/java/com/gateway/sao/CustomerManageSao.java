package com.gateway.sao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.alibaba.fastjson.JSONObject;
import com.gateway.config.HytriFallBackFactory;

@FeignClient(value="CustomerManage",fallback=HytriFallBackFactory.class)
public interface CustomerManageSao {

	
	
	@GetMapping("/customer/user/login")
	public String login(@RequestBody JSONObject json,@RequestHeader("X-Real-IP")String realIp);
	
	
	@GetMapping("/customer/user/register")
	public String resgister(@RequestBody JSONObject json,@RequestHeader("X-Real-IP")String realIp);
	
	
}

package com.microservice.controller;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.microservice.model.AppUser;
import com.microservice.sao.ProducerSao;
import com.microservice.service.impl.AppUserServiceImpl;
import com.rnmg.jace.utils.ResponseInfo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jqiang-jace
 * @since 2019-06-26
 */
@RestController
@RequestMapping("/appUser")
@Slf4j
public class AppUserController {

	
	@Autowired
	private AppUserServiceImpl appUserService;
	
	@Autowired
	private ProducerSao sao;
	
	@Resource(name="redisTemplate")
	private RedisTemplate redis;
	
	@RequestMapping("/api/{id}")
	public ResponseInfo<AppUser> show(@PathVariable("id")Integer id){
		log.info("-------------------------------show--------------------------");
//		String res = sao.callProducerSao(id);
		ResponseInfo<AppUser> result =appUserService.findOneById(id);
		return result;
	}
	
	@RequestMapping("/api/add/{username}")
	public ResponseInfo<String> add(@PathVariable("username")String userName,@RequestParam("password")String passWord,@RequestParam("phoneNo")String phoneNo){
		log.info("-------------------------------add--------------------------");
		return appUserService.save(userName, passWord, phoneNo);
	}
	
}

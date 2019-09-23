package com.xinyunfu.controller;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyunfu.model.AppUser;
import com.xinyunfu.sao.ProducerSao;
import com.xinyunfu.service.impl.AppUserServiceImpl;
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
	
	
	@RequestMapping("/api/queryById/{id}")
	public ResponseInfo<AppUser> queryById(@PathVariable("id")Integer id){
		log.info("-------------------------------show--------------------------");
//		String res = sao.callProducerSao(id);
		ResponseInfo<AppUser> result =appUserService.findOneById(id);
		return result;
	}
	
	@RequestMapping("/api/add/{username}")
	public ResponseInfo<String> add(AppUser user){
		log.info("-------------------------------add--------------------------");
		return appUserService.saveUser(user);
	}
	@RequestMapping("/api/update")
	public ResponseInfo<String> update(AppUser user){
		log.info("-------------------------------upate--------------------------");
		//AppUser appUser =JSONObject.parseObject(user,AppUser.class);
		return appUserService.update(user);
	}
	@RequestMapping("/api/queryByPage")
	public ResponseInfo<IPage<AppUser>> queryByPage(AppUser user,Integer page,Integer pageSize){
		log.info("-------------------------------upate--------------------------");
		return appUserService.queryByPage(user,page,pageSize);
	}
}

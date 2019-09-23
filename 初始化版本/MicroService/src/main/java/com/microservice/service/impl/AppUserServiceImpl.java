package com.microservice.service.impl;

import com.microservice.model.AppUser;
import com.microservice.sao.ProducerSao;
import com.microservice.mapper.AppUserMapper;
import com.microservice.service.IAppUserService;
import com.microservice.utils.RedisCommonUtil;
import com.rnmg.jace.utils.ResponseInfo;

import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jqiang-jace
 * @since 2019-06-26
 */
@Service
@Slf4j
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {

	@Autowired
	private ProducerSao sao;
	
	@Autowired
	private RedisCommonUtil redisUtil; 
	
	private static String USER_CACHE_KEY="user_cache";
	public ResponseInfo<AppUser> findOneById(int id){
		//AppUser appUser = this.getById(id);
		
		Object cache = redisUtil.getCache(USER_CACHE_KEY+id);
		String resultString =(cache==null? null: (String)cache);
		if(cache==null) {
		    resultString = sao.callProducerSao(id);
		    redisUtil.setCache(USER_CACHE_KEY+id, resultString);
		}
		log.info("resultString :{}",resultString);
		return ResponseInfo.success(null);
	}
	
	
	
	public ResponseInfo<String> save(String userName,String passWord,String phoneNo){
		AppUser au = new AppUser();
		au.setPassWrod(passWord).setUserName(userName).setPhoneNo(phoneNo);
		this.save(au);
		return ResponseInfo.success("添加成功");
		
	}
}

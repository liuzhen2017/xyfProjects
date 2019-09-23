package com.xinyunfu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rnmg.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.AppUserMapper;
import com.xinyunfu.model.AppUser;
import com.xinyunfu.sao.ProducerSao;
import com.xinyunfu.service.IAppUserService;
import com.xinyunfu.utils.RedisCommonUtil;

import lombok.extern.slf4j.Slf4j;

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
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ResponseInfo<AppUser> findOneById(int id){
		//AppUser appUser = this.getById(id);
		
		Object cache = redisUtil.getCache(USER_CACHE_KEY+id);
		String resultString =(cache==null? null: (String)cache);
		if(cache==null) {
		    resultString = sao.callProducerSao(id);
		    redisUtil.setCache(USER_CACHE_KEY+id, resultString);
		}
		log.info("resultString :{}",resultString);
		return ResponseInfo.success(JSONObject.parseObject(resultString,AppUser.class));
	}
	
	
	/**
	 * 保存用户信息
	 * @param userName
	 * @param passWord
	 * @param phoneNo
	 * @return
	 */
	public ResponseInfo<String> saveUser(AppUser user){
		if(this.save(user)) {
			return ResponseInfo.success("新增成功!");
		}else {
			return ResponseInfo.success("新增失败!");
		}
		
	}
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public ResponseInfo<String> update(AppUser user){
		if(this.updateById(user)) {
			return ResponseInfo.success("修改成功!");
		};
		return ResponseInfo.success("修改失败!!");
	}
	/**
	 * 分页查询
	 * @param user
	 * @return
	 */
	public ResponseInfo<IPage<AppUser>> queryByPage(AppUser user,Integer page,Integer pageSize){
	    Page<AppUser> pages = new Page<AppUser>(page == null ? 1 : page , pageSize == null ? 15 : pageSize);
        QueryWrapper<AppUser> queryWrapper =  new QueryWrapper<>();
         IPage<AppUser> page2 = this.page(pages, queryWrapper);
		return ResponseInfo.success(page2) ;
	}
}

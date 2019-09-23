package com.xinyunfu.mapper;

import com.xinyunfu.model.UserPush;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jace
 * @since 2019-07-04
 */
@Mapper
public interface UserPushMapper extends BaseMapper<UserPush> {

	
	@Select("select pusher_no_linked from user_push where user_no=#{userNo}")
	String getPushList(String userNo);

	@Select("select * from user_push")
	List<UserPush> findAll();

	
	
}

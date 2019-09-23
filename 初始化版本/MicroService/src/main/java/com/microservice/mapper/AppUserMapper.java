package com.microservice.mapper;

import com.microservice.model.AppUser;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jqiang-jace
 * @since 2019-06-26
 */
@Mapper
public interface AppUserMapper extends BaseMapper<AppUser> {

}

package com.example.demo.dao.cm;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.CusUser;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jace
 * @since 2019-08-28
 */
@Mapper
public interface CusUserMapper extends BaseMapper<CusUser> {

	
	@Select("select * from cus_user where id=#{id}")
	public CusUser findOne(int id);
}

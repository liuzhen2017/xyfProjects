package com.xinyunfu.mapper;

import com.xinyunfu.model.PayLog;

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
 * @since 2019-07-08
 */
@Mapper
public interface PayLogMapper extends BaseMapper<PayLog> {

	
	@Select("select * from pay_log where is_submit=1 and main_order_no = #{mainOrderNo}")
	public List<PayLog> getNoPayByMainOrderNo(String mainOrderNo) ;
}

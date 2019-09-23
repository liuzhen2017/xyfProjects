package com.example.demo.dao.om;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.OrderMessage;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jace
 * @since 2019-08-28
 */
@Mapper
public interface OrderMessageMapper extends BaseMapper<OrderMessage> {

	
	@Select("select * from order_message where order_no=#{orderNo}")
	public OrderMessage findOne(String orderNo);
	
	
	@Select("select * from order_message")
	public List<OrderMessage> findAll();
}

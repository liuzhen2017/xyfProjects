package com.xinyunfu.mapper;

import com.xinyunfu.model.OrderTree;
import com.xinyunfu.model.UserPackageOrder;

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
public interface OrderTreeMapper extends BaseMapper<OrderTree> {

	
	
	/**
	 * 
	 * @param nodeNo
	 * @return
	 */
	@Select("select * from order_tree where enables=1 and node_no=#{nodeNo} limit 1")
	public OrderTree getByNodeNo(String nodeNo);
	
	
	@Select("select * from order_tree where enables=1 and user_no=#{userNo}")
	public OrderTree getOrderTreeByUserNo(String userNo);
	
	
	@Select("select count(0) from order_tree where enables=1 and main_order_no=#{mainOrderNo}")
	public int getVailNodeNo(String mainOrderNo);
	
	/**
	 * 将节点置为失效
	 * @param nodeNo
	 * @return
	 */
	@Select("update order_tree set enables=0 where node_no=#{nodeNo}")
	public void updateSetEnables(String nodeNo);



	@Select("select is_submit from user_package_order where main_order_no=#{mainOrderNo}")
	public Integer getMainOrderSubmit(String mainOrderNo);
}

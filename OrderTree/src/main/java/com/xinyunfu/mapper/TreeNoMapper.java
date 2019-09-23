package com.xinyunfu.mapper;

import com.xinyunfu.model.TreeNo;
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
 * @since 2019-07-08
 */
@Mapper
public interface TreeNoMapper extends BaseMapper<TreeNo> {

	
	
	
	/**
	 * 获取有效节点数
	 * @param userNo
	 * @return
	 */
	@Select("select valid_leaf_count from tree_no where enables=1 and node_no = (select node_no from order_tree where enables=1 and user_no=#{userNo} )")
	public int getVaildNodeCount(String userNo);
	
	
	/**
	 * 获取有效节点
	 * @param userNo
	 * @return
	 */
	@Select("select * from tree_no where enables=1 and node_no = (select node_no from order_tree where user_no=#{userNo} and enables=1 order by created_date limit 1)")
	public TreeNo getValidTreeNoByUserNo(String userNo);
	
	
	/**
	 * 根据主订单编号，获取有效的节点编号
	 * @param mainOrderNo
	 * @return
	 */
	@Select("select * from tree_no where node_no=(select node_no from order_tree where main_order_no=#{mainOrderNo})  ")
	public TreeNo getNodeByMainOrderNo(String mainOrderNo);
	
	
	@Select("select * from tree_no where node_no=#{nodeNo}")
	public TreeNo getNodeByNodeNo(String nodeNo);
	
	
	/**
	 * 获取6缺6，懒用户
	 * @return
	 */      
	@Select("SELECT * FROM tree_no WHERE enables=1 AND valid_leaf_count=6 ORDER BY submit_date LIMIT 1")
	public TreeNo getLazyUser();
	
	
	@Select("select * from user_package_order where enables=1 and main_order_no=(select main_order_no from order_tree where node_no=#{nodeNo})")
	public UserPackageOrder getUserPackageOrder(String nodeNo);
	
	
	@Select("select * from tree_no where enables=1 and valid_leaf_count=#{vildCount} ORDER BY submit_date LIMIT 1")
	public TreeNo getTreeNoByVildCount(int vildCount);

}

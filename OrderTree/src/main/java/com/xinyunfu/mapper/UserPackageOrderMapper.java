package com.xinyunfu.mapper;

import com.xinyunfu.model.UserPackageOrder;

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
 * @since 2019-07-06
 */
@Mapper
public interface UserPackageOrderMapper extends BaseMapper<UserPackageOrder> {

	
	
	/**
	 *	查询是否存在有效的订单未完成
	 * @param userNo
	 * @return
	 */
	@Select("select count(0) from order_tree where user_no=#{userNo} and enables=1")
	public int getCountByUserNo(String userNo);
	
	
	@Select("select * from user_package_order where is_submit=1 and main_order_no=#{orderNo}")
	public UserPackageOrder getOneByMainOrderNo(String orderNo);
	
	/**
	 *	查询剩余有效订单数量
	 * @param userNo
	 * @return
	 */
	@Select("select sum(vaild_count) from user_package_order where user_no=#{userNo} and enables=1")
	public int getPackageCount(String userNo);
	
	
	@Select("select * from user_package_order where user_no=#{userNo} and enables=1 order by created_date limit 1")
	public UserPackageOrder getOneByUserNo(String userNo);

    /**
     * 查询有效的数据
     * @return
     */
    @Select("select * from user_package_order where enables = 1")
	public List<UserPackageOrder> findAll();
    
    
    @Select("update user_package_order set is_submit=0 where main_order_no=#{mainOrderNo}")
    public void updateSubmitByOrderNo(String mainOrderNo);
    
    
    @Select("select unit_price from user_package_order where main_order_no=#{orderNo}")
    public int getUnitPriceByOrderNo(String orderNo);
}

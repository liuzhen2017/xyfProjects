package com.ruoyi.service;

import com.ruoyi.domain.CouponManger;
import java.util.List;

/**
 * 优惠券 服务层
 * 
 * @author ruoyi
 * @date 2019-08-04
 */
public interface ICouponMangerService 
{
	/**
     * 查询优惠券信息
     * 
     * @param id 优惠券ID
     * @return 优惠券信息
     */
	public CouponManger selectCouponMangerById(Long id);
	
	/**
     * 查询优惠券列表
     * 
     * @param couponManger 优惠券信息
     * @return 优惠券集合
     */
	public List<CouponManger> selectCouponMangerList(CouponManger couponManger);
	
	/**
     * 新增优惠券
     * 
     * @param couponManger 优惠券信息
     * @return 结果
     */
	public int insertCouponManger(CouponManger couponManger);
	
	/**
     * 修改优惠券
     * 
     * @param couponManger 优惠券信息
     * @return 结果
     */
	public int updateCouponManger(CouponManger couponManger);
		
	/**
     * 删除优惠券信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCouponMangerByIds(String ids);
	
}

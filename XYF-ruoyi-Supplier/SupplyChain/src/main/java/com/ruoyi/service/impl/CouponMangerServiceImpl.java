package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.CouponMangerMapper;
import com.ruoyi.domain.CouponManger;
import com.ruoyi.service.ICouponMangerService;
import com.ruoyi.common.core.text.Convert;

/**
 * 优惠券 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-04
 */
@Service
public class CouponMangerServiceImpl implements ICouponMangerService 
{
	@Autowired
	private CouponMangerMapper couponMangerMapper;

	/**
     * 查询优惠券信息
     * 
     * @param id 优惠券ID
     * @return 优惠券信息
     */
    @Override
	public CouponManger selectCouponMangerById(Long id)
	{
	    return couponMangerMapper.selectCouponMangerById(id);
	}
	
	/**
     * 查询优惠券列表
     * 
     * @param couponManger 优惠券信息
     * @return 优惠券集合
     */
	@Override
	public List<CouponManger> selectCouponMangerList(CouponManger couponManger)
	{
	    return couponMangerMapper.selectCouponMangerList(couponManger);
	}
	
    /**
     * 新增优惠券
     * 
     * @param couponManger 优惠券信息
     * @return 结果
     */
	@Override
	public int insertCouponManger(CouponManger couponManger)
	{
	    return couponMangerMapper.insertCouponManger(couponManger);
	}
	
	/**
     * 修改优惠券
     * 
     * @param couponManger 优惠券信息
     * @return 结果
     */
	@Override
	public int updateCouponManger(CouponManger couponManger)
	{
	    return couponMangerMapper.updateCouponManger(couponManger);
	}

	/**
     * 删除优惠券对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCouponMangerByIds(String ids)
	{
		return couponMangerMapper.deleteCouponMangerByIds(Convert.toStrArray(ids));
	}
	
}

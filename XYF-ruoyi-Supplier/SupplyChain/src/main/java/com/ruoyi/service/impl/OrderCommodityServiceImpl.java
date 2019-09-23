package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.OrderCommodityMapper;
import com.ruoyi.domain.OrderCommodity;
import com.ruoyi.service.IOrderCommodityService;
import com.ruoyi.common.core.text.Convert;

/**
 * 订单商品 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-16
 */
@Service
public class OrderCommodityServiceImpl implements IOrderCommodityService 
{
	@Autowired
	private OrderCommodityMapper orderCommodityMapper;

	/**
     * 查询订单商品信息
     * 
     * @param id 订单商品ID
     * @return 订单商品信息
     */
    @Override
	public OrderCommodity selectOrderCommodityById(Long id)
	{
	    return orderCommodityMapper.selectOrderCommodityById(id);
	}
	
	/**
     * 查询订单商品列表
     * 
     * @param orderCommodity 订单商品信息
     * @return 订单商品集合
     */
	@Override
	public List<OrderCommodity> selectOrderCommodityList(OrderCommodity orderCommodity)
	{
	    return orderCommodityMapper.selectOrderCommodityList(orderCommodity);
	}
	
    /**
     * 新增订单商品
     * 
     * @param orderCommodity 订单商品信息
     * @return 结果
     */
	@Override
	public int insertOrderCommodity(OrderCommodity orderCommodity)
	{
	    return orderCommodityMapper.insertOrderCommodity(orderCommodity);
	}
	
	/**
     * 修改订单商品
     * 
     * @param orderCommodity 订单商品信息
     * @return 结果
     */
	@Override
	public int updateOrderCommodity(OrderCommodity orderCommodity)
	{
	    return orderCommodityMapper.updateOrderCommodity(orderCommodity);
	}

	/**
     * 删除订单商品对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteOrderCommodityByIds(String ids)
	{
		return orderCommodityMapper.deleteOrderCommodityByIds(Convert.toStrArray(ids));
	}
	
}

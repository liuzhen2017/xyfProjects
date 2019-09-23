package com.ruoyi.service;

import com.ruoyi.domain.OrderCommodity;
import java.util.List;

/**
 * 订单商品 服务层
 * 
 * @author ruoyi
 * @date 2019-08-16
 */
public interface IOrderCommodityService 
{
	/**
     * 查询订单商品信息
     * 
     * @param id 订单商品ID
     * @return 订单商品信息
     */
	public OrderCommodity selectOrderCommodityById(Long id);
	
	/**
     * 查询订单商品列表
     * 
     * @param orderCommodity 订单商品信息
     * @return 订单商品集合
     */
	public List<OrderCommodity> selectOrderCommodityList(OrderCommodity orderCommodity);
	
	/**
     * 新增订单商品
     * 
     * @param orderCommodity 订单商品信息
     * @return 结果
     */
	public int insertOrderCommodity(OrderCommodity orderCommodity);
	
	/**
     * 修改订单商品
     * 
     * @param orderCommodity 订单商品信息
     * @return 结果
     */
	public int updateOrderCommodity(OrderCommodity orderCommodity);
		
	/**
     * 删除订单商品信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteOrderCommodityByIds(String ids);
	
}

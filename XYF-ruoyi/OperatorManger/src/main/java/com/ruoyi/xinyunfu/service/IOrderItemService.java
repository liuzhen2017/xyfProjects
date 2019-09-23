package com.ruoyi.xinyunfu.service;

import com.ruoyi.xinyunfu.domain.OrderItem;
import java.util.List;

/**
 * 订单子 服务层
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
public interface IOrderItemService 
{
	/**
     * 查询订单子信息
     * 
     * @param itemId 订单子ID
     * @return 订单子信息
     */
	public OrderItem selectOrderItemById(Long itemId);
	
	/**
     * 查询订单子列表
     * 
     * @param orderItem 订单子信息
     * @return 订单子集合
     */
	public List<OrderItem> selectOrderItemList(OrderItem orderItem);
	
	/**
     * 新增订单子
     * 
     * @param orderItem 订单子信息
     * @return 结果
     */
	public int insertOrderItem(OrderItem orderItem);
	
	/**
     * 修改订单子
     * 
     * @param orderItem 订单子信息
     * @return 结果
     */
	public int updateOrderItem(OrderItem orderItem);
		
	/**
     * 删除订单子信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteOrderItemByIds(String ids);
	
}

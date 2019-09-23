package com.ruoyi.xinyunfu.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xinyunfu.mapper.OrderItemMapper;
import com.ruoyi.xinyunfu.domain.OrderItem;
import com.ruoyi.xinyunfu.service.IOrderItemService;
import com.ruoyi.common.core.text.Convert;

/**
 * 订单子 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
@Service
public class OrderItemServiceImpl implements IOrderItemService 
{
	@Autowired
	private OrderItemMapper orderItemMapper;

	/**
     * 查询订单子信息
     * 
     * @param itemId 订单子ID
     * @return 订单子信息
     */
    @Override
	public OrderItem selectOrderItemById(Long itemId)
	{
	    return orderItemMapper.selectOrderItemById(itemId);
	}
	
	/**
     * 查询订单子列表
     * 
     * @param orderItem 订单子信息
     * @return 订单子集合
     */
	@Override
	public List<OrderItem> selectOrderItemList(OrderItem orderItem)
	{
	    return orderItemMapper.selectOrderItemList(orderItem);
	}
	
    /**
     * 新增订单子
     * 
     * @param orderItem 订单子信息
     * @return 结果
     */
	@Override
	public int insertOrderItem(OrderItem orderItem)
	{
	    return orderItemMapper.insertOrderItem(orderItem);
	}
	
	/**
     * 修改订单子
     * 
     * @param orderItem 订单子信息
     * @return 结果
     */
	@Override
	public int updateOrderItem(OrderItem orderItem)
	{
	    return orderItemMapper.updateOrderItem(orderItem);
	}

	/**
     * 删除订单子对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteOrderItemByIds(String ids)
	{
		return orderItemMapper.deleteOrderItemByIds(Convert.toStrArray(ids));
	}
	
}

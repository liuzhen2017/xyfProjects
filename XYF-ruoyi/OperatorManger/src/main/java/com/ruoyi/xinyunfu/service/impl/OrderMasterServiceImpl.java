package com.ruoyi.xinyunfu.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xinyunfu.mapper.OrderMasterMapper;
import com.ruoyi.xinyunfu.domain.OrderMaster;
import com.ruoyi.xinyunfu.service.IOrderMasterService;
import com.ruoyi.common.core.text.Convert;

/**
 * 订单主 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
@Service
public class OrderMasterServiceImpl implements IOrderMasterService 
{
	@Autowired
	private OrderMasterMapper orderMasterMapper;

	/**
     * 查询订单主信息
     * 
     * @param orderId 订单主ID
     * @return 订单主信息
     */
    @Override
	public OrderMaster selectOrderMasterById(Long orderId)
	{
	    return orderMasterMapper.selectOrderMasterById(orderId);
	}
	
	/**
     * 查询订单主列表
     * 
     * @param orderMaster 订单主信息
     * @return 订单主集合
     */
	@Override
	public List<OrderMaster> selectOrderMasterList(OrderMaster orderMaster)
	{
	    return orderMasterMapper.selectOrderMasterList(orderMaster);
	}
	
    /**
     * 新增订单主
     * 
     * @param orderMaster 订单主信息
     * @return 结果
     */
	@Override
	public int insertOrderMaster(OrderMaster orderMaster)
	{
	    return orderMasterMapper.insertOrderMaster(orderMaster);
	}
	
	/**
     * 修改订单主
     * 
     * @param orderMaster 订单主信息
     * @return 结果
     */
	@Override
	public int updateOrderMaster(OrderMaster orderMaster)
	{
	    return orderMasterMapper.updateOrderMaster(orderMaster);
	}

	/**
     * 删除订单主对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteOrderMasterByIds(String ids)
	{
		return orderMasterMapper.deleteOrderMasterByIds(Convert.toStrArray(ids));
	}
	
}

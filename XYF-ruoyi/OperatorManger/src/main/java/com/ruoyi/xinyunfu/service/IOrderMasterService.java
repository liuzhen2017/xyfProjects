package com.ruoyi.xinyunfu.service;

import com.ruoyi.xinyunfu.domain.OrderMaster;
import java.util.List;

/**
 * 订单主 服务层
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
public interface IOrderMasterService 
{
	/**
     * 查询订单主信息
     * 
     * @param orderId 订单主ID
     * @return 订单主信息
     */
	public OrderMaster selectOrderMasterById(Long orderId);
	
	/**
     * 查询订单主列表
     * 
     * @param orderMaster 订单主信息
     * @return 订单主集合
     */
	public List<OrderMaster> selectOrderMasterList(OrderMaster orderMaster);
	
	/**
     * 新增订单主
     * 
     * @param orderMaster 订单主信息
     * @return 结果
     */
	public int insertOrderMaster(OrderMaster orderMaster);
	
	/**
     * 修改订单主
     * 
     * @param orderMaster 订单主信息
     * @return 结果
     */
	public int updateOrderMaster(OrderMaster orderMaster);
		
	/**
     * 删除订单主信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteOrderMasterByIds(String ids);
	
}

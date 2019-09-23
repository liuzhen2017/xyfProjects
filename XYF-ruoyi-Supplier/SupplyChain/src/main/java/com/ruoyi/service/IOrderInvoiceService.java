package com.ruoyi.service;

import com.ruoyi.domain.OrderInvoice;
import java.util.List;

/**
 * 订单发票 服务层
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
public interface IOrderInvoiceService 
{
	/**
     * 查询订单发票信息
     * 
     * @param id 订单发票ID
     * @return 订单发票信息
     */
	public OrderInvoice selectOrderInvoiceById(String id);
	
	/**
     * 查询订单发票列表
     * 
     * @param orderInvoice 订单发票信息
     * @return 订单发票集合
     */
	public List<OrderInvoice> selectOrderInvoiceList(OrderInvoice orderInvoice);
	
	/**
     * 新增订单发票
     * 
     * @param orderInvoice 订单发票信息
     * @return 结果
     */
	public int insertOrderInvoice(OrderInvoice orderInvoice);
	
	/**
     * 修改订单发票
     * 
     * @param orderInvoice 订单发票信息
     * @return 结果
     */
	public int updateOrderInvoice(OrderInvoice orderInvoice);
		
	/**
     * 删除订单发票信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteOrderInvoiceByIds(String ids);
	
}

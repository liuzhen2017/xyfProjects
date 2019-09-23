package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.OrderInvoiceMapper;
import com.ruoyi.domain.OrderInvoice;
import com.ruoyi.service.IOrderInvoiceService;
import com.ruoyi.common.core.text.Convert;

/**
 * 订单发票 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
@Service
public class OrderInvoiceServiceImpl implements IOrderInvoiceService 
{
	@Autowired
	private OrderInvoiceMapper orderInvoiceMapper;

	/**
     * 查询订单发票信息
     * 
     * @param id 订单发票ID
     * @return 订单发票信息
     */
    @Override
	public OrderInvoice selectOrderInvoiceById(String id)
	{
	    return orderInvoiceMapper.selectOrderInvoiceById(id);
	}
	
	/**
     * 查询订单发票列表
     * 
     * @param orderInvoice 订单发票信息
     * @return 订单发票集合
     */
	@Override
	public List<OrderInvoice> selectOrderInvoiceList(OrderInvoice orderInvoice)
	{
	    return orderInvoiceMapper.selectOrderInvoiceList(orderInvoice);
	}
	
    /**
     * 新增订单发票
     * 
     * @param orderInvoice 订单发票信息
     * @return 结果
     */
	@Override
	public int insertOrderInvoice(OrderInvoice orderInvoice)
	{
	    return orderInvoiceMapper.insertOrderInvoice(orderInvoice);
	}
	
	/**
     * 修改订单发票
     * 
     * @param orderInvoice 订单发票信息
     * @return 结果
     */
	@Override
	public int updateOrderInvoice(OrderInvoice orderInvoice)
	{
	    return orderInvoiceMapper.updateOrderInvoice(orderInvoice);
	}

	/**
     * 删除订单发票对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteOrderInvoiceByIds(String ids)
	{
		return orderInvoiceMapper.deleteOrderInvoiceByIds(Convert.toStrArray(ids));
	}
	
}

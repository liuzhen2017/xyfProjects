package com.xinyunfu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.model.OrderConsignee;

import java.util.List;

/**
 * <p>
 * 订单收货表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
public interface IOrderConsigneeService extends IService<OrderConsignee> {

    /**
     * 根据订单编号获取 送货地址详细信息
     * @param orderNo
     * @return
     */
    OrderConsignee getConsigneeByOrderNo(String currentUserId,String orderNo);

    /**
     * 添加收货地址
     * @param currentUserId
     * @param itemNos 订单集合
     * @param addressId
     * @return
     */
    boolean add(String currentUserId,List<String> itemNos, long addressId);



}

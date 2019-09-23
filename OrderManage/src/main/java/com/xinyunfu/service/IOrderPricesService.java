package com.xinyunfu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.model.OrderPrices;

/**
 * <p>
 * 订单价格清单表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
public interface IOrderPricesService extends IService<OrderPrices> {

    /**
     * 获取我的 价格清单
     * @param currentUserId
     * @param current
     * @param size
     * @return
     */
    IPage<OrderPrices> getMyOrderPrices(String currentUserId, Integer current, Integer size);

    /**
     * 根据主订单编号获取 价格清单
     * @param orderNo
     * @param current
     * @param size
     * @return
     */
    IPage<OrderPrices>  getOrderPricesByMasterNo(String orderNo,Integer current,Integer size);

    /**
     * 根据子订单编号获取 价格清单
     * @param orderNo
     * @param current
     * @param size
     * @return
     */
    IPage<OrderPrices>  getOrderPricesByItemNo(String orderNo,Integer current,Integer size);



}

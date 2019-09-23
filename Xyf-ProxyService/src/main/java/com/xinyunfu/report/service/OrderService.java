package com.xinyunfu.report.service;


import com.xinyunfu.report.model.*;

import java.util.*;

/**
 * @author XRZ
 * @date 2019/9/2
 * @Description :
 */
public interface OrderService {
    /**
     * 获取订单购买数量折线图
     * @param parmat
     * @return
     * @throws Exception
     */
    public Map<String,Object> queryOrderLineData()throws Exception;

    /**
     * 获取订单购买数量饼图
     * @param parmat
     * @return
     * @throws Exception
     */
    public Map<String,Object> queryOrderPieData()throws Exception;

    /**
     * 获取订单购买量集合
     * @param parmat orderItem
     * @return 订单购买量集合
     * @throws Exception
     */
    public List<OrderItem> selectOrderBuyNumList(OrderItem orderItem);
}

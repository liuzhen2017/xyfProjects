package com.xinyunfu.report.service.impl;

import com.xinyunfu.report.dao.proxy.OrderMapper;
import com.xinyunfu.report.model.*;
import com.xinyunfu.report.service.*;
import com.xinyunfu.report.utils.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author XRZ
 * @date 2019/9/2
 * @Description :
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IEchartsFactoryService echartsFactoryService;

    @Override
    public  Map<String,Object> queryOrderLineData() throws Exception {
        //用户注册信息
        List<Map<String, Object>> listOrderBuyInfo = orderMapper.selectOrderBuyCount();
        List<Map<String, Object>> source[] = new ArrayList[1];
        Map<String, Object> map=new HashMap<>();
        source[0]=listOrderBuyInfo;
        String title[]=new String[]{"订单购买数量"};
        try {
            map= echartsFactoryService.createdLine(source, title);
            return map;
        } catch (Exception e) {
            log.error("queryCustData is Error,Msg =", e.getMessage(), e);
            return map;
        }
    }

    @Override
    public  Map<String,Object> queryOrderPieData() throws Exception {
        //用户注册信息
        List<Map<String, Object>> listOrderBuyInfo = orderMapper.selectOrderBuyCount();
        Map<String, Object> map=new HashMap<>();
        String title[]=new String[]{"订单购买数量"};
        try {
            map= echartsFactoryService.createdPie(listOrderBuyInfo,title);
            return map;
        } catch (Exception e) {
            log.error("queryPieData is Error,Msg =", e.getMessage(), e);
            return map;
        }
    }

    /**
     * 获取订单购买量集合
     * @param parmat orderItem
     * @return 订单购买量集合
     * @throws Exception
     */
    public List<OrderItem> selectOrderBuyNumList(OrderItem orderItem){
        return  orderMapper.selectOrderBuyNumList(orderItem);
    }
}

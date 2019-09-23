package com.xinyunfu.report.controller;

import com.ruoyi.common.core.controller.*;
import com.ruoyi.common.core.page.*;
import com.xinyunfu.report.model.*;
import com.xinyunfu.report.service.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author XRZ
 * @date 2019/9/3
 * @Description :
 */
@RestController
@RequestMapping("orderdata")
@Slf4j
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("reportLine")
    public Map<String,Object> reportOrderLineData(){
        Map<String, Object> map=new HashMap<String, Object>();
        try{
            map= orderService.queryOrderLineData();
            return map;
        }catch(Exception e){
            log.error("=====请求异常=，msg={}",e.getMessage());
        }
        return map;
    }

    @RequestMapping("reportPie")
    public Map<String,Object> reportOrderPieData(){
        Map<String, Object> map=new HashMap<String, Object>();
        try{
            map= orderService.queryOrderPieData();
            return map;
        }catch(Exception e){
            log.error("=====请求异常=，msg={}",e.getMessage());
        }
        return map;
    }

    /**
     * 查询订单购买量集合
     */
    @RequestMapping("/queryOrderBuyPage")
    public List<OrderItem> list(OrderItem orderItem)
    {
        return orderService.selectOrderBuyNumList(orderItem);
    }
}

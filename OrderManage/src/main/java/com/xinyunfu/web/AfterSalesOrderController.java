package com.xinyunfu.web;


import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.dto.OrderDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.IAfterSalesHandleService;
import com.xinyunfu.service.IAfterSalesOrderService;
import com.xinyunfu.utils.RedisCommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 售后订单表 前端控制器
 * </p>
 *
 * @author Xurongze
 * @since 2019-09-10
 */
@RestController
@RequestMapping("/afterSalesOrder")
public class AfterSalesOrderController {

    @Autowired
    private RedisCommonUtil redis;
    @Autowired
    private IAfterSalesHandleService iAfterSalesHandleService;
    @Autowired
    private IAfterSalesOrderService iAfterSalesOrderService;


//    /**
//     * 申请退款
//     * @param vo 订单号
//     * @return
//     */
//    @PostMapping("/requsetArefund")
//    public ResponseInfo<Object> requsetArefund(@RequestBody OrderDTO vo){
//        if(StringUtils.isEmpty(vo.getOrderNo()))
//            throw new CustomException(ExecptionEnum.ERROR_PARAM);
//    }

}

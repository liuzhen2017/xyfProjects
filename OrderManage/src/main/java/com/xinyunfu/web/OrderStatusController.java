package com.xinyunfu.web;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.dto.OrderStatusDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.IOrderCouponsService;
import com.xinyunfu.service.IOrderItemService;
import com.xinyunfu.service.IOrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author XRZ
 * @date 2019/8/13
 * @Description : 查询订单状态
 */
@RestController
@RequestMapping("/OrderStatus")
public class OrderStatusController {

    @Autowired
    private IOrderCouponsService iOrderCouponsService;
    @Autowired
    private IOrderMasterService iOrderMasterService;

    /**
     *  根据订单号获取 订单状态 即支付金额
     * @param
     * @return
     */
    @PostMapping("/getStatus")
    public ResponseInfo<OrderStatusDTO> getStatus(@RequestBody OrderStatusDTO vo){
        if(vo.getType() == 0) //主订单
            return ResponseInfo.success(iOrderMasterService.getStatus(vo.getOrderNo()));
        if(vo.getType() == 1) //优惠券
            return ResponseInfo.success(iOrderCouponsService.getStatus(vo.getOrderNo()));
        throw new CustomException(ExecptionEnum.ERROR_PARAM);
    }


}

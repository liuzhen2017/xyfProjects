package com.xinyunfu.web;


import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.OrderConsignee;
import com.xinyunfu.service.IOrderConsigneeService;
import com.xinyunfu.dto.OrderDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单收货 表 前端控制器
 *
 * @author Xurongze
 * @since 2019-07-06
 */
@RestController
@RequestMapping("/orderConsignee")
public class OrderConsigneeController {

    @Autowired
    private IOrderConsigneeService iOrderConsigneeService;

    /**
     * 根据订单编号获取 送货地址详细信息
     * @param orderNo
     * @return
     */
    @PostMapping("/getConsigneeByOrderId")
    public ResponseInfo<OrderConsignee> getConsigneeByOrderNo(@RequestHeader(Common.UID) String currentUserId,
                                                              @RequestBody OrderDTO vo){
        if(StringUtils.isEmpty(vo.getOrderNo()))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        return ResponseInfo.success(iOrderConsigneeService.getConsigneeByOrderNo(currentUserId, vo.getOrderNo()));
    }




}

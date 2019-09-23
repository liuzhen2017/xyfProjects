package com.xinyunfu.dto;

import com.xinyunfu.model.OrderConsignee;
import com.xinyunfu.model.OrderItem;
import com.xinyunfu.model.OrderMaster;
import com.xinyunfu.model.PayType;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/22
 * @Description : 订单详情对象
 */
@Data
public class OrderDetailsDTO implements Serializable {

    /**
     * 主订单对象
     */
    private OrderMaster orderMaster;

    /**
     * 子订单对象
     */
    private OrderItem orderItem;

    /**
     * 收货地址对象
     */
    private OrderConsignee orderConsignee;

    /**
     * 支持的支付方式
     */
    private List<PayType> list = new ArrayList<>();

}

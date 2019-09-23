package com.xinyunfu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author XRZ
 * @date 2019/8/13
 * @Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDTO implements Serializable {

    /**
     * 订单号
     */
    private Long orderNo = 0l;

    /**
     * 订单类型 （主订单=0，优惠券=1）
     */
    private Integer type = -1;

    /**
     * 订单状态 true 为已成功支付
     */
    private Boolean status;

    /**
     * 支付金额
     */
    private BigDecimal money;


    public OrderStatusDTO(Boolean status, BigDecimal money) {
        this.status = status;
        this.money = money;
    }
}

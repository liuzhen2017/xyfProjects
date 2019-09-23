package com.xinyunfu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/9 0009
 * @Description : 下单时传输的对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderDTO implements Serializable {

    /**
     * 商家集合
     */
    private List<GoodsDTO> goodsVos;

    /**
     * 购物车ID 集合
     */
    private List<Long> cartIds;

    /**
     * 收货地址的ID
     */
    private Long addressId;

    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 发票ID
     */
    private String invoiceId;


}

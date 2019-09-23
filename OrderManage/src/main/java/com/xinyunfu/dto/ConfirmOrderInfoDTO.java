package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/23
 * @Description : 确认订单信息DTO
 */
@Data
public class ConfirmOrderInfoDTO implements Serializable {

    /**
     * SKUID
     */
    private Long skuId;

    /**
     * 购买的数量
     */
    private Integer num;

    /**
     * 购物车ID
     */
    private Long cartId = 0l;

    /**
     * 使用券ID的集合
     */
    private List<String> coupons;

}

package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author XRZ
 * @date 2019/7/18
 * @Description :
 */
@Data
public class CartDTO implements Serializable {

    /**
     * 购物车ID数组
     */
    private Long[] cartIds;

    /**
     * 购物车ID
     */
    private Long cartId;

    /**
     * 商品数量
     */
    private Integer number = 0;

    /**
     * SKU_ID
     */
    private Long skuId = 0l;

    /**
     * SKU_ID 说明
     */
    private String skuDesc;
}

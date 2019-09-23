package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/22
 * @Description : 商品列表
 */
@Data
public class Clist implements Serializable {

    /**
     * SKU ID
     */
    private String skuId;

    /**
     * 可使用券的ID集合
     */
    private Long[] coupons;

    /**
     * 购买数量
     */
    private Integer num;

}


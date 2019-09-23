package com.xinyunfu.dto.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FreightDTO implements Serializable {

    /**
     * 商品ID
     */
    private Long proId;

    /**
     * 邮费
     */
    private BigDecimal postage = new BigDecimal(0);

    /**
     * 单价
     */
    private BigDecimal price = new BigDecimal(0);

    /**
     *  最低价格
     */
    private BigDecimal minSellPrice = new BigDecimal(0);

    /**
     * 成本价格
     */
    private BigDecimal costPrice = new BigDecimal(0);

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * SKUID说明
     */
    private String skuDesc;

    /**
     * 商品的状态 （上架=0，下架=1，删除=2）
     */
    private Integer status;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 商家的ID
     */
    private Long storeId;

    /**
     * 商家的名字
     */
    private String storeName;

    /**
     * 商品名字
     */
    private String proName;

    /**
     * 首图URL
     */
    private String imgUrl;

    /**
     * 商品来源(0=其他,1=京东,2=怡亚通)
     */
    private Integer source;

}
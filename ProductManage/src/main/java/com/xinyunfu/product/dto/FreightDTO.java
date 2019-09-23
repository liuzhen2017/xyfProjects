package com.xinyunfu.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class FreightDTO implements Serializable{
    /**
     * 商品id
     */
	private Long proId;
    /**
     * 邮费
     */
	private BigDecimal postage;
    /**
     * 售价
     */
	private BigDecimal price;
    /**
     * 券后价
     */
	private BigDecimal minSellPrice;
    /**
     * 成本价
     */
	private BigDecimal costPrice;
    /**
     * skuId
     */
    private Long skuId;
    /**
     * 状态
     */
	private Integer status;
    /**
     * sku库存
     */
	private Integer stock;
    /**
     * 商家id
     */
	private Long storeId;
    /**
     * 商家名称
     */
	private String storeName;
    /**
     * 商品名称
     */
	private String proName;
    /**
     * 图片路径
     */
	private String imgUrl;
    /**
     * sku规格描述
     */
	private String skuDesc;
    /**
     * 商品来源
     */
	private Integer source;

}

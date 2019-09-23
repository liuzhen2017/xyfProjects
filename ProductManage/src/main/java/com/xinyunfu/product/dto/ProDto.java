package com.xinyunfu.product.dto;

import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ProDto implements Serializable{
    /**
     * 商品id
     */
	private Long proId;
    /**
     * 商品名称
     */
	private String proName;
    /**
     * 首图
     */
	private String imageUrl;
    /**
     * 图片背景颜色(商品展示在首页轮播图位置时需要)
     */
	private String color;
    /**
     * 排序号
     */
	private Integer sortNumber;
    /**
     * 价格
     */
	private BigDecimal price;
    /**
     * 券后价格
     */
	private BigDecimal minPrice;
    /**
     * 秒杀价格
     */
	private BigDecimal seckillPrice;
    /**
     * 秒杀销量
     */
	private Integer allSeckillSellStock;
    /**
     * 秒杀库存
     */
	private Integer allSellStock;
    /**
     * 总库存
     */
	private Integer allStock;
    /**
     * 商品来源 0自营,1京东,2怡亚通,其他待定
     */
	private Integer source;
    /**
     * 总销量
     */
	private Integer allSeckillStock;
    /**
     * 秒杀状态
     */
	private Integer killStatus;
}

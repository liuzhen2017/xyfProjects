package com.xinyunfu.product.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pro_sku")
public class ProSku extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.AUTO)
	private Long skuId;
	/** 对应第三方skuId */
	private String skuNo;
    /**组编号,属性id拼接属性值id */
	private String groupNo;
	/**商品id */
	private Long proId;
	private Integer stock;                //库存
	private Integer sellStock;            //已售出数量
    private Integer seckillStock;         //秒杀库存
    private Integer seckillSellStock;     //秒杀销量
	private BigDecimal price;             //售价
    private BigDecimal seckillPrice;      //秒杀售价
	private String imgUrl;                //sku图片,没有使用商品默认图片
	private BigDecimal integralPrice;     //抵扣积分
	private BigDecimal marketPrice;       //市场价
    @JsonIgnore
	private BigDecimal minSellPrice;      //最低售价
    @JsonIgnore
	private BigDecimal costPrice;         //成本价
	private String dataVersion;           //版本号

}

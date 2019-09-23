package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pro_sku")
public class ProSku extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.AUTO)
	private Long skuId;
	private String skuNo;
	private String groupNo;               //组编号,属性拼接属性值
	private Long proId;
	private Integer stock;                //库存
	private Integer sellStock;            //已售出数量
    private Integer seckillStock;         //秒杀库存
    private Integer seckillSellStock;     //秒杀销量
	private BigDecimal price;             //售价
    private BigDecimal seckillPrice;       //秒杀售价
	private String imgUrl;                //sku图片,没有使用商品默认图片
	private BigDecimal integralPrice;     //抵扣积分
	private BigDecimal marketPrice;       //市场价
	private BigDecimal minSellPrice;      //最低售价
	private BigDecimal costPrice;         //成本价
	private String dataVersion;           //版本号

}

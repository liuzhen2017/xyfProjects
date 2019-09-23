package com.xinyunfu.commons.goods;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProSku extends BaseModel{

	private Long skuId;
	private String skuNo;		// 商品编号，对应jd的skuId，需要转换类型
	private String groupNo;		// 选择颜色，型号等规格
	private Long proId;
	private Integer stock;		//当前库存
	private Integer sellStock;		//已出售量
	private String imgUrl;                //sku图片,没有使用商品默认图片
	private BigDecimal price;		//出售价格（JD价）
	private BigDecimal integralPrice;
	private BigDecimal marketPrice;		//市场价：原价。出售价格的1.3倍。
	private BigDecimal minSellPrice;      //最低价
	private BigDecimal costPrice;		//成本价，对应京东的协议价
	private BigDecimal rebate;         // 折扣
	private String dataVersion;

}

package com.xinyunfu.commons.goods;


import lombok.*;
import lombok.experimental.Accessors;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain=true)
@Builder
public class Product extends BaseModel{

	private Long proId;
	
	private String proNo;		//对应 JD的sku
	
	private String proName;
	
	private String proTitle;
	
	private Integer storeId;

	private Integer status;		//状态，上架=0, 下架=1, 删除=2, 待审核99
	
	private Double weight;
	
	private Integer source;		//商品来源,0其他,1京东,2怡亚通
	
	private String unit;		//商品计量单位

	private Long freightId;

}

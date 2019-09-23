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

	private String proNo;	//对应 JD的sku

	private String proName;

	private Integer status;            //状态，启用=0,禁用=1,删除=2

	private String proTitle;
	private String buyerReading;
	private Long storeId;
	private String storeName;
	private Long freightId;
	private Double weight;
	private Integer killStatus;
	private Integer source;		//商品来源,0其他,1京东,2怡亚通
	private Integer proType;
	private String unit;
	private Integer sortNumber;   //排序号
	private String couponType;     //支持的券类型 0不支持   拼接券的id
	private String couponTypeName;  //券的名称

}

package com.ruoyi.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain=true)
@TableName("product")
public class Product extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long proId;
	
	private String proNo;
	
	private String proName;
	
	private String proTitle;
	private String buyerReading;
	private Long storeId;
	private String storeName;
	private Long freightId;
	private Double weight;
	private Integer killStatus;
	private Integer source;
	private Integer proType;
	private String unit;

	private String couponType;     //支持的券类型 0不支持   拼接券的id
    private String couponTypeName;  //券的名称

}

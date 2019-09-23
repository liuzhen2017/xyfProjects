package com.xinyunfu.product.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("pro_freight")
public class Freight extends BaseModel{

	@TableId(type = IdType.AUTO)
	private Long freightId;
	private Long storeId;
	private Integer type;              //是否包邮,0包邮,1不包邮,2按地区包邮,3按金额包邮,4按件数包邮
	private String conditions;         //包邮条件
	private String modes;              //计算邮费,json格式保存,省_市:邮费策略(如:1_15_1_2)
	private BigDecimal defaultPostage; //默认邮费
}

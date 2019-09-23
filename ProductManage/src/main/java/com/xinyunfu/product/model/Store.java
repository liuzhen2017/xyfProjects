package com.xinyunfu.product.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("store")
public class Store extends BaseModel{

	@TableId(type = IdType.AUTO)
	private Long storeId;             //商家id
	private String storeName;         //商家名称
	private String ownerId;           //所有者,关联供应链的cust_no
	private String tel;               //客服电话
	private Integer storeType;        //商户类型（商城商户=1,联盟商户=2,系统商户=100）
}

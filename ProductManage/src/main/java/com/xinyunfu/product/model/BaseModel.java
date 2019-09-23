package com.xinyunfu.product.model;

import java.io.Serializable;
import java.security.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
public class BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Integer status;            //状态，启用=0,禁用=1,删除=2
	private String remarks;            //备注
	private String createdTime;     //创建时间
	@JsonIgnore
	private String createdBy;          //创建人
	@JsonIgnore
	private String updatedTime;     //修改时间
	@JsonIgnore
	private String updatedBy;          //修改人
}

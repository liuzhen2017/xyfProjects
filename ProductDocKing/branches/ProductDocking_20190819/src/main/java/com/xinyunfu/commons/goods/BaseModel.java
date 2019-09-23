package com.xinyunfu.commons.goods;

import java.io.Serializable;
import java.security.Timestamp;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
public class BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer status;            //状态，启用=0,禁用=1,删除=2
	private String remarks;            //备注
	private Timestamp createdTime;     //创建时间
	private String createdBy;          //创建人
	private Timestamp updatedTime;     //修改时间
	private String updatedBy;          //修改人
}

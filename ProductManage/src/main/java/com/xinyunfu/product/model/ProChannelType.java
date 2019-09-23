package com.xinyunfu.product.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.security.Timestamp;

/**
 * 商品分类类型表 pro_channel_type
 * 
 * @author TYM
 * @date 2019-08-30
 */
@Data
@Accessors(chain = true)
@TableName("pro_channel_type")
public class ProChannelType implements Serializable
{

	/** 类目类型编号 */
	@TableId(type = IdType.AUTO)
	private Long channelTypeId;
	/** 类目类型名称 */
	private String channelTypeName;
    private Integer status;            //状态，启用=0,禁用=1,删除=2
    private String remarks;            //备注
    private String createdTime;     //创建时间
    private String createdBy;          //创建人
    private String updatedTime;     //修改时间
    private String updatedBy;          //修改人



}

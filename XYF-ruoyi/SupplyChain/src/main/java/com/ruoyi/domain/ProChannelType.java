package com.ruoyi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import java.sql.Timestamp;

/**
 * 商品类型分类表 pro_channel_type
 * 
 * @author ruoyi
 * @date 2019-08-30
 */
@Data
public class ProChannelType
{
	private static final long serialVersionUID = 1L;
	
	/** 类目类型编号 */
	private String channelTypeId;
	/** 类目类型名称 */
	private String channelTypeName;
	/** 状态，启用=0,禁用=1,删除=2 */
	private Integer status;
	/** 备注 */
	private String remarks;
    private String createdTime;     //创建时间

    private String createdBy;          //创建人

    private String updatedTime;     //修改时间

    private String updatedBy;          //修改人
}

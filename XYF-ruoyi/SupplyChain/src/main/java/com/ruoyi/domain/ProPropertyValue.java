package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 商品属性值表 pro_property_value
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public class ProPropertyValue extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 属性值id */
	private Long valueId;
	/** 属性id */
	private Long propertyId;
	/** 属性值 */
	private String valueText;
	/** 状态，启用=0,禁用=1,删除=2 */
	private Integer status;
	/** 备注 */
	private String remarks;
	/** 创建时间 */
	private Date createdTime;
	/** 创建人 */
	private String createdBy;
	/** 修改时间 */
	private Date updatedTime;
	/** 修改人 */
	private String updatedBy;

	public void setValueId(Long valueId) 
	{
		this.valueId = valueId;
	}

	public Long getValueId() 
	{
		return valueId;
	}
	public void setPropertyId(Long propertyId) 
	{
		this.propertyId = propertyId;
	}

	public Long getPropertyId() 
	{
		return propertyId;
	}
	public void setValueText(String valueText) 
	{
		this.valueText = valueText;
	}

	public String getValueText() 
	{
		return valueText;
	}
	public void setStatus(Integer status) 
	{
		this.status = status;
	}

	public Integer getStatus() 
	{
		return status;
	}
	public void setremarks(String remarks)
	{
		this.remarks = remarks;
	}

	public String getRemarks()
	{
		return remarks;
	}
	public void setCreatedTime(Date createdTime)
	{
		this.createdTime= createdTime;
	}

	public Date getCreatedTime()
	{
		return createdTime;
	}
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}
	public void setUpdatedTime(Date updatedTime)
	{
		this.updatedTime = updatedTime;
	}

	public Date getUpdatedTime()
	{
		return updatedTime;
	}
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("valueId", getValueId())
            .append("propertyId", getPropertyId())
            .append("valueText", getValueText())
            .append("status", getStatus())
            .append("remarks", getRemarks())
            .append("createdTime", getCreatedTime())
            .append("createdBy", getCreatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .toString();
    }
}

package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 商品属性表 pro_property
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public class ProProperty extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 属性编号 */
	private Long propertyId;
	/** 属性名称 */
	private String propertyName;
	/** 商品编号 */
	private Long proId;
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

	public void setPropertyId(Long propertyId) 
	{
		this.propertyId = propertyId;
	}

	public Long getPropertyId() 
	{
		return propertyId;
	}
	public void setPropertyName(String propertyName) 
	{
		this.propertyName = propertyName;
	}

	public String getPropertyName() 
	{
		return propertyName;
	}
	public void setProId(Long proId) 
	{
		this.proId = proId;
	}

	public Long getProId() 
	{
		return proId;
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
            .append("propertyId", getPropertyId())
            .append("propertyName", getPropertyName())
            .append("proId", getProId())
            .append("status", getStatus())
            .append("remarks", getRemarks())
            .append("createdTime", getCreatedTime())
            .append("createdBy", getCreatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .toString();
    }
}

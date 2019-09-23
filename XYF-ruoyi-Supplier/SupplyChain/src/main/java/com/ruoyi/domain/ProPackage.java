package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 套餐表 pro_package
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public class ProPackage extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 套餐id */
	private Long packageId;
	/** 商品id */
	private Long proId;
	/** 套餐名称 */
	private String packageName;
	/** 套餐价格 */
	private BigDecimal packagePrice;
	/** 状态，启用=0,禁用=1,删除=2 */
	private Integer status;
	/** 创建时间 */
	private Date createdTime;
	/** 创建人 */
	private String createdBy;
	/** 修改时间 */
	private Date updatedTime;
	/** 修改人 */
	private String updatedBy;

	public void setPackageId(Long packageId) 
	{
		this.packageId = packageId;
	}

	public Long getPackageId() 
	{
		return packageId;
	}
	public void setProId(Long proId) 
	{
		this.proId = proId;
	}

	public Long getProId() 
	{
		return proId;
	}
	public void setPackageName(String packageName) 
	{
		this.packageName = packageName;
	}

	public String getPackageName() 
	{
		return packageName;
	}
	public void setPackagePrice(BigDecimal packagePrice) 
	{
		this.packagePrice = packagePrice;
	}

	public BigDecimal getPackagePrice() 
	{
		return packagePrice;
	}
	public void setStatus(Integer status) 
	{
		this.status = status;
	}

	public Integer getStatus() 
	{
		return status;
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
            .append("packageId", getPackageId())
            .append("proId", getProId())
            .append("packageName", getPackageName())
            .append("packagePrice", getPackagePrice())
            .append("status", getStatus())
            .append("createdTime", getCreatedTime())
            .append("createdBy", getCreatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .toString();
    }
}

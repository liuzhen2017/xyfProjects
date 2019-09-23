package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 商家表 store
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public class Store extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 商家id */
	private Long storeId;
	/** 商家名称 */
	private String storeName;
	/** 所有者,关联user表的id */
	private String ownerId;
	/** 状态，启用=0,禁用=1,删除=2 */
	private Integer status;
	/** 客服电话 */
	private String tel;
	/** '商户类型（商城商户=1,联盟商户=2,系统商户=100） */
	private Integer storeType;
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

	public void setStoreId(Long storeId) 
	{
		this.storeId = storeId;
	}

	public Long getStoreId() 
	{
		return storeId;
	}
	public void setStoreName(String storeName) 
	{
		this.storeName = storeName;
	}

	public String getStoreName() 
	{
		return storeName;
	}
	public void setOwnerId(String ownerId)
	{
		this.ownerId = ownerId;
	}

	public String getOwnerId()
	{
		return ownerId;
	}
	public void setStatus(Integer status) 
	{
		this.status = status;
	}

	public Integer getStatus() 
	{
		return status;
	}
	public void setTel(String tel) 
	{
		this.tel = tel;
	}

	public String getTel() 
	{
		return tel;
	}
	public void setStoreType(Integer storeType) 
	{
		this.storeType = storeType;
	}

	public Integer getStoreType() 
	{
		return storeType;
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
            .append("storeId", getStoreId())
            .append("storeName", getStoreName())
            .append("ownerId", getOwnerId())
            .append("status", getStatus())
            .append("tel", getTel())
            .append("storeType", getStoreType())
            .append("remarks", getRemarks())
            .append("createdTime", getCreatedTime())
            .append("createdBy", getCreatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .toString();
    }
}

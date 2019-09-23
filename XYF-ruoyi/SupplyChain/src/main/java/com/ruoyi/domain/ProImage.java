package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品图片表 pro_image
 * 
 * @author ruoyi
 * @date 2019-08-04
 */
public class ProImage extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 图片编号 */
	private Long id;
	/** 商品编号 */
	private Long proId;
	/** 图片的绝对路径 */
	private String imgUrl;
	/** 是否为默认显示,1为默认 */
	private Integer isDefault;
	/** 状态，启用=0,禁用=1,删除=2,待审核=99 */
	private Integer status;
	/** 备注 */
	private String remarks;

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setProId(Long proId) 
	{
		this.proId = proId;
	}

	public Long getProId() 
	{
		return proId;
	}
	public void setImgUrl(String imgUrl) 
	{
		this.imgUrl = imgUrl;
	}

	public String getImgUrl() 
	{
		return imgUrl;
	}
	public void setIsDefault(Integer isDefault) 
	{
		this.isDefault = isDefault;
	}

	public Integer getIsDefault() 
	{
		return isDefault;
	}
	public void setStatus(Integer status) 
	{
		this.status = status;
	}

	public Integer getStatus() 
	{
		return status;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("proId", getProId())
            .append("imgUrl", getImgUrl())
            .append("isDefault", getIsDefault())
            .append("status", getStatus())
            .append("remarks", getRemarks())
            .append("createdTime", getCreatedTime())
            .append("createdBy", getCreatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .toString();
    }
}

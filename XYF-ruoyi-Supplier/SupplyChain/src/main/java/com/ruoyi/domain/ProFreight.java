package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.math.BigDecimal;

/**
 * 店铺邮费模板表 pro_freight
 * 
 * @author ruoyi
 * @date 2019-08-05
 */
public class ProFreight extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 邮费模板id */
	private Long freightId;
	/** 模板名称 */
	private String freightName;
	/** 店铺id */
	private Long storeId;
	/** 是否包邮,0包邮,1不包邮,2按地区包邮,3按金额包邮,4按件数包邮, */
	private Integer type;
	/** 包邮条件 */
	private String conditions;
	/** 计算邮费,json格式保存 */
	private String modes;
	/** 默认邮费 */
	private BigDecimal defaultPostage;
	/** 备注 */
	private String remarks;
	/** 状态,0启用,1禁用,2删除 */
	private Integer status;

	public void setFreightId(Long freightId) 
	{
		this.freightId = freightId;
	}

	public Long getFreightId() 
	{
		return freightId;
	}
	public void setFreightName(String freightName) 
	{
		this.freightName = freightName;
	}

	public String getFreightName() 
	{
		return freightName;
	}
	public void setStoreId(Long storeId) 
	{
		this.storeId = storeId;
	}

	public Long getStoreId() 
	{
		return storeId;
	}
	public void setType(Integer type) 
	{
		this.type = type;
	}

	public Integer getType() 
	{
		return type;
	}
	public void setConditions(String conditions) 
	{
		this.conditions = conditions;
	}

	public String getConditions() 
	{
		return conditions;
	}
	public void setModes(String modes) 
	{
		this.modes = modes;
	}

	public String getModes() 
	{
		return modes;
	}
	public void setDefaultPostage(BigDecimal defaultPostage) 
	{
		this.defaultPostage = defaultPostage;
	}

	public BigDecimal getDefaultPostage() 
	{
		return defaultPostage;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
	}
	public void setStatus(Integer status) 
	{
		this.status = status;
	}

	public Integer getStatus() 
	{
		return status;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("freightId", getFreightId())
            .append("freightName", getFreightName())
            .append("storeId", getStoreId())
            .append("type", getType())
            .append("conditions", getConditions())
            .append("modes", getModes())
            .append("defaultPostage", getDefaultPostage())
            .append("remarks", getRemarks())
            .append("status", getStatus())
            .append("createdTime", getCreatedTime())
            .append("createdBy", getCreatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .toString();
    }
}

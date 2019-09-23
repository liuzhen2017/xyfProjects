package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 订单发票表 order_invoice
 * 
 * @author ruoyi
 * @date 2019-08-02
 */

public class OrderInvoice extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 唯一的id */
	private String id;
	/** 发票类型（电子发票=0，纸只发票=1） */
	private Integer type;
	/** 发票抬头（个人=0，企业=1） */
	private Integer payable;
	/** 邮箱 */
	private String email;
	/** 单位名称 */
	private String unitName;
	/** 纳税人识别号 */
	private String taxpayerNumber;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/** 创建时间 */
	private Date createdTime;
	/** 最后修改时间 */
	private Date updatedTime;
	/** 是否可用（可用=1，禁用=0） */
	private Integer enable;
	private Integer status;
	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setType(Integer type) 
	{
		this.type = type;
	}

	public Integer getType() 
	{
		return type;
	}
	public void setPayable(Integer payable) 
	{
		this.payable = payable;
	}

	public Integer getPayable() 
	{
		return payable;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getEmail() 
	{
		return email;
	}
	public void setUnitName(String unitName) 
	{
		this.unitName = unitName;
	}

	public String getUnitName() 
	{
		return unitName;
	}
	public void setTaxpayerNumber(String taxpayerNumber) 
	{
		this.taxpayerNumber = taxpayerNumber;
	}

	public String getTaxpayerNumber() 
	{
		return taxpayerNumber;
	}
	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}

	public Date getCreatedTime()
	{
		return createdTime;
	}
	public void setUpdatedTime(Date updatedTime)
	{
		this.updatedTime = updatedTime;
	}

	public Date getUpdatedTime()
	{
		return updatedTime;
	}
	public void setEnable(Integer enable) 
	{
		this.enable = enable;
	}

	public Integer getEnable() 
	{
		return enable;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("payable", getPayable())
            .append("email", getEmail())
            .append("unitName", getUnitName())
            .append("taxpayerNumber", getTaxpayerNumber())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("enable", getEnable())
            .toString();
    }
}

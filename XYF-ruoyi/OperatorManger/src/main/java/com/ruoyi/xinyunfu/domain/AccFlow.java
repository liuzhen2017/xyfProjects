package com.ruoyi.xinyunfu.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 账户流水表 acc_flow
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
public class AccFlow extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 序号 */
	private Integer id;
	/** 订单父编号 */
	private String superOrderNumber;
	/** 订单编号 */
	private String orderNumber;
	/** 用户编号_支付方 */
	private String userNo;
	/** 支付成功后的支付编号 */
	private String payNumber;
	/** 付款账号 */
	private String payAccNo;
	/** 付款账号名称 */
	private String payAccName;
	/** 收款账号 */
	private String receAccNo;
	/** 收款名称 */
	private String receAccName;
	/** 付款金额 */
	private BigDecimal payAmount;
	/** 交易时间 */
	private Date tranDate;
	/** 交易类型 */
	private String tranType;
	/** 业务状态 */
	private String busiStatus;
	/** 是否可用（可用=1，禁用=0） */
	private Integer enable;
	/** 创建时间 */
	private Date createdTime;
	/** 创建人 */
	private String createdBy;
	/** 最后修改时间 */
	private Date updatedTime;
	/** 最后修改人 */
	private String updatedBy;
	/**  */
	private String channel;
	/**  */
	private String subject;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setSuperOrderNumber(String superOrderNumber) 
	{
		this.superOrderNumber = superOrderNumber;
	}

	public String getSuperOrderNumber() 
	{
		return superOrderNumber;
	}
	public void setOrderNumber(String orderNumber) 
	{
		this.orderNumber = orderNumber;
	}

	public String getOrderNumber() 
	{
		return orderNumber;
	}
	public void setUserNo(String userNo) 
	{
		this.userNo = userNo;
	}

	public String getUserNo() 
	{
		return userNo;
	}
	public void setPayNumber(String payNumber) 
	{
		this.payNumber = payNumber;
	}

	public String getPayNumber() 
	{
		return payNumber;
	}
	public void setPayAccNo(String payAccNo) 
	{
		this.payAccNo = payAccNo;
	}

	public String getPayAccNo() 
	{
		return payAccNo;
	}
	public void setPayAccName(String payAccName) 
	{
		this.payAccName = payAccName;
	}

	public String getPayAccName() 
	{
		return payAccName;
	}
	public void setReceAccNo(String receAccNo) 
	{
		this.receAccNo = receAccNo;
	}

	public String getReceAccNo() 
	{
		return receAccNo;
	}
	public void setReceAccName(String receAccName) 
	{
		this.receAccName = receAccName;
	}

	public String getReceAccName() 
	{
		return receAccName;
	}
	public void setPayAmount(BigDecimal payAmount) 
	{
		this.payAmount = payAmount;
	}

	public BigDecimal getPayAmount() 
	{
		return payAmount;
	}
	public void setTranDate(Date tranDate) 
	{
		this.tranDate = tranDate;
	}

	public Date getTranDate() 
	{
		return tranDate;
	}
	public void setTranType(String tranType) 
	{
		this.tranType = tranType;
	}

	public String getTranType() 
	{
		return tranType;
	}
	public void setBusiStatus(String busiStatus) 
	{
		this.busiStatus = busiStatus;
	}

	public String getBusiStatus() 
	{
		return busiStatus;
	}
	public void setEnable(Integer enable) 
	{
		this.enable = enable;
	}

	public Integer getEnable() 
	{
		return enable;
	}
	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
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
	public void setChannel(String channel) 
	{
		this.channel = channel;
	}

	public String getChannel() 
	{
		return channel;
	}
	public void setSubject(String subject) 
	{
		this.subject = subject;
	}

	public String getSubject() 
	{
		return subject;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("superOrderNumber", getSuperOrderNumber())
            .append("orderNumber", getOrderNumber())
            .append("userNo", getUserNo())
            .append("payNumber", getPayNumber())
            .append("payAccNo", getPayAccNo())
            .append("payAccName", getPayAccName())
            .append("receAccNo", getReceAccNo())
            .append("receAccName", getReceAccName())
            .append("payAmount", getPayAmount())
            .append("tranDate", getTranDate())
            .append("tranType", getTranType())
            .append("busiStatus", getBusiStatus())
            .append("enable", getEnable())
            .append("createdTime", getCreatedTime())
            .append("createdBy", getCreatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("channel", getChannel())
            .append("subject", getSubject())
            .toString();
    }
}

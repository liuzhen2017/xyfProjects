package com.ruoyi.xinyunfu.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 订单主表 order_master
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
public class OrderMaster extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 订单id */
	private Long orderId;
	/** 订单编号 唯一 */
	private String orderNo;
	/** 地址ID */
	private Long addressId;
	/** 用户的id */
	private String userId;
	/** 支付金额 */
	private BigDecimal payAmount;
	/** 订单类型（购买商品=00，购买套餐=01） */
	private String orderType;
	/** 创建时间 */
	private Date createdTime;
	/** 创建人id */
	private String createdBy;
	/** 最后修改时间 */
	private Date updatedTime;
	/** 最后修改人id */
	private String updatedBy;
	/** 是否可用（可用=1，禁用=0） */
	private Integer enable;

	public void setOrderId(Long orderId) 
	{
		this.orderId = orderId;
	}

	public Long getOrderId() 
	{
		return orderId;
	}
	public void setOrderNo(String orderNo) 
	{
		this.orderNo = orderNo;
	}

	public String getOrderNo() 
	{
		return orderNo;
	}
	public void setAddressId(Long addressId) 
	{
		this.addressId = addressId;
	}

	public Long getAddressId() 
	{
		return addressId;
	}
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getUserId() 
	{
		return userId;
	}
	public void setPayAmount(BigDecimal payAmount) 
	{
		this.payAmount = payAmount;
	}

	public BigDecimal getPayAmount() 
	{
		return payAmount;
	}
	public void setOrderType(String orderType) 
	{
		this.orderType = orderType;
	}

	public String getOrderType() 
	{
		return orderType;
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
            .append("orderId", getOrderId())
            .append("orderNo", getOrderNo())
            .append("addressId", getAddressId())
            .append("userId", getUserId())
            .append("payAmount", getPayAmount())
            .append("orderType", getOrderType())
            .append("createdTime", getCreatedTime())
            .append("createdBy", getCreatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("enable", getEnable())
            .toString();
    }
}

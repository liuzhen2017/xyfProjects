package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 向用户转账表 ebank_transfer
 * 
 * @author ruoyi
 * @date 2019-08-05
 */
public class EbankTransfer
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 转账订单编号，其它服务的订单编号 */
	private String orderNo;
	/** 合并后的订单编号，合并订单是的订单编号 */
	private String mergeOrderNo;
	/** 资金转出用户 */
	private Long sourceUserNo;
	/** 转出用户类型 */
	private String sourceAccountType;
	/** 资金转入用户 */
	private Long receiveUserNo;
	/** 转入用户类型 */
	private String receiveAccountType;
	/** 付款金额 */
	private BigDecimal amount;
	/** 是否已关闭 */
	private Integer closed;
	/** 发起支付请求的服务 */
	private String serverName;
	/** 乐观锁 */
	private Integer version;
	/** 创建时间 */
	private Date createTime;
	/** 最后修改时间 */
	private Date lastModifyTime;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setOrderNo(String orderNo) 
	{
		this.orderNo = orderNo;
	}

	public String getOrderNo() 
	{
		return orderNo;
	}
	public void setMergeOrderNo(String mergeOrderNo) 
	{
		this.mergeOrderNo = mergeOrderNo;
	}

	public String getMergeOrderNo() 
	{
		return mergeOrderNo;
	}
	public void setSourceUserNo(Long sourceUserNo) 
	{
		this.sourceUserNo = sourceUserNo;
	}

	public Long getSourceUserNo() 
	{
		return sourceUserNo;
	}
	public void setSourceAccountType(String sourceAccountType) 
	{
		this.sourceAccountType = sourceAccountType;
	}

	public String getSourceAccountType() 
	{
		return sourceAccountType;
	}
	public void setReceiveUserNo(Long receiveUserNo) 
	{
		this.receiveUserNo = receiveUserNo;
	}

	public Long getReceiveUserNo() 
	{
		return receiveUserNo;
	}
	public void setReceiveAccountType(String receiveAccountType) 
	{
		this.receiveAccountType = receiveAccountType;
	}

	public String getReceiveAccountType() 
	{
		return receiveAccountType;
	}
	public void setAmount(BigDecimal amount) 
	{
		this.amount = amount;
	}

	public BigDecimal getAmount() 
	{
		return amount;
	}
	public void setClosed(Integer closed) 
	{
		this.closed = closed;
	}

	public Integer getClosed() 
	{
		return closed;
	}
	public void setServerName(String serverName) 
	{
		this.serverName = serverName;
	}

	public String getServerName() 
	{
		return serverName;
	}
	public void setVersion(Integer version) 
	{
		this.version = version;
	}

	public Integer getVersion() 
	{
		return version;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setLastModifyTime(Date lastModifyTime) 
	{
		this.lastModifyTime = lastModifyTime;
	}

	public Date getLastModifyTime() 
	{
		return lastModifyTime;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("mergeOrderNo", getMergeOrderNo())
            .append("sourceUserNo", getSourceUserNo())
            .append("sourceAccountType", getSourceAccountType())
            .append("receiveUserNo", getReceiveUserNo())
            .append("receiveAccountType", getReceiveAccountType())
            .append("amount", getAmount())
            .append("closed", getClosed())
            .append("serverName", getServerName())
            .append("version", getVersion())
            .append("createTime", getCreateTime())
            .append("lastModifyTime", getLastModifyTime())
            .toString();
    }
}

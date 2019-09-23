package com.ruoyi.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 商品支付类型关联表 pro_pay_type
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Data
@Accessors(chain = true)
public class ProPayType
{
	private static final long serialVersionUID = 1L;


	private Long id;

	/** 商品id */
	private Long proId;
	/** 支付类型id */
	private Long payTypeId;
	/** 状态 */
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

//	public void setProId(Long proId)
//	{
//		this.proId = proId;
//	}
//
//	public Long getProId()
//	{
//		return proId;
//	}
//	public void setPayTypeId(Long payTypeId)
//	{
//		this.payTypeId = payTypeId;
//	}
//
//	public Long getPayTypeId()
//	{
//		return payTypeId;
//	}
//	public void setStatus(Integer status)
//	{
//		this.status = status;
//	}
//
//	public Integer getStatus()
//	{
//		return status;
//	}
//	public void setremarks(String remarks)
//	{
//		this.remarks = remarks;
//	}
//
//	public String getRemarks()
//	{
//		return remarks;
//	}
//	public void setCreatedTime(Date createdTime)
//	{
//		this.createdTime= createdTime;
//	}
//
//	public Date getCreatedTime()
//	{
//		return createdTime;
//	}
//	public void setCreatedBy(String createdBy)
//	{
//		this.createdBy = createdBy;
//	}
//
//	public String getCreatedBy()
//	{
//		return createdBy;
//	}
//	public void setUpdatedTime(Date updatedTime)
//	{
//		this.updatedTime = updatedTime;
//	}
//
//	public Date getUpdatedTime()
//	{
//		return updatedTime;
//	}
//	public void setUpdatedBy(String updatedBy)
//	{
//		this.updatedBy = updatedBy;
//	}
//
//	public String getUpdatedBy()
//	{
//		return updatedBy;
//	}
//
//    public String toString() {
//        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
//            .append("proId", getProId())
//            .append("payTypeId", getPayTypeId())
//            .append("status", getStatus())
//            .append("remarks", getRemarks())
//            .append("createdTime", getCreatedTime())
//            .append("createdBy", getCreatedBy())
//            .append("updatedTime", getUpdatedTime())
//            .append("updatedBy", getUpdatedBy())
//            .toString();
//    }
}

package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 供应链表 supply_chain_manger
 *
 * @author ruoyi
 * @date 2019-08-03
 */
public class SupplyChainManger extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** ID */
	private Integer id;
	/** 企业名称 */
	private String custName;
	/** 企业地址 */
	private String custAdd;
	/** 联系人电话 */
	private String contactTel;
	/** 联系人 */
	private String contactName;
	/** 邮箱 */
	private String email;
	/** 信用代码 */
	private String creditCode;
	/** 营业执照_图片 */
	private String businessLicenseImg;
	/** 传真 */
	private String fax;
	/** 结算周期 */
	private Integer settlementCycle;
	/** 创建时间 */
	private Date createDate;
	/** 修改人 */
	private String updatdBy;
	/** 修改时间 */
	private Date updatdDate;
	/** 状态 */
	private String busiStatus;
	/** 备用字段 */
	private String standbyField1;
	/** 备用字段 */
	private String standbyField2;

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getId()
	{
		return id;
	}
	public void setCustName(String custName)
	{
		this.custName = custName;
	}

	public String getCustName()
	{
		return custName;
	}
	public void setCustAdd(String custAdd)
	{
		this.custAdd = custAdd;
	}

	public String getCustAdd()
	{
		return custAdd;
	}
	public void setContactTel(String contactTel)
	{
		this.contactTel = contactTel;
	}

	public String getContactTel()
	{
		return contactTel;
	}
	public void setContactName(String contactName)
	{
		this.contactName = contactName;
	}

	public String getContactName()
	{
		return contactName;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}
	public void setCreditCode(String creditCode)
	{
		this.creditCode = creditCode;
	}

	public String getCreditCode()
	{
		return creditCode;
	}
	public void setBusinessLicenseImg(String businessLicenseImg)
	{
		this.businessLicenseImg = businessLicenseImg;
	}

	public String getBusinessLicenseImg()
	{
		return businessLicenseImg;
	}
	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public String getFax()
	{
		return fax;
	}
	public void setSettlementCycle(Integer settlementCycle)
	{
		this.settlementCycle = settlementCycle;
	}

	public Integer getSettlementCycle()
	{
		return settlementCycle;
	}
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public Date getCreateDate()
	{
		return createDate;
	}
	public void setUpdatdBy(String updatdBy)
	{
		this.updatdBy = updatdBy;
	}

	public String getUpdatdBy()
	{
		return updatdBy;
	}
	public void setUpdatdDate(Date updatdDate)
	{
		this.updatdDate = updatdDate;
	}

	public Date getUpdatdDate()
	{
		return updatdDate;
	}
	public void setBusiStatus(String busiStatus)
	{
		this.busiStatus = busiStatus;
	}

	public String getBusiStatus()
	{
		return busiStatus;
	}
	public void setStandbyField1(String standbyField1)
	{
		this.standbyField1 = standbyField1;
	}

	public String getStandbyField1()
	{
		return standbyField1;
	}
	public void setStandbyField2(String standbyField2)
	{
		this.standbyField2 = standbyField2;
	}

	public String getStandbyField2()
	{
		return standbyField2;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
				.append("id", getId())
				.append("custName", getCustName())
				.append("custAdd", getCustAdd())
				.append("contactTel", getContactTel())
				.append("contactName", getContactName())
				.append("email", getEmail())
				.append("creditCode", getCreditCode())
				.append("businessLicenseImg", getBusinessLicenseImg())
				.append("fax", getFax())
				.append("settlementCycle", getSettlementCycle())
				.append("createDate", getCreateDate())
				.append("createdBy", getCreatedBy())
				.append("updatdBy", getUpdatdBy())
				.append("updatdDate", getUpdatdDate())
				.append("busiStatus", getBusiStatus())
				.append("standbyField1", getStandbyField1())
				.append("standbyField2", getStandbyField2())
				.toString();
	}
}

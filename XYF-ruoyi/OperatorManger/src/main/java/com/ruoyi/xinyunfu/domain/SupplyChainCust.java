package com.ruoyi.xinyunfu.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 供应链表 supply_chain_cust
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
public class SupplyChainCust extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 企业名称 */
	private String custName;
	/** 企业地址 */
	private String custAdd;
	/** 联系电话 */
	private String contactsTel;
	/** 联系人 */
	private String contactsName;
	/** 传真 */
	private String fax;
	/** 邮箱 */
	private String email;
	/** 结算周期 */
	private Integer settCycle;
	/** 业务状态 */
	private String busiStatus;
	/**  */
	private Date createdTime;
	/**  */
	private Date updatedTime;
	/**  */
	private String updatedBy;
	/** 营业执照 */
	private String businessLicenseImg;
	/** 统一社会代码 */
	private String unifyingSocialCredit;

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
	public void setContactsTel(String contactsTel) 
	{
		this.contactsTel = contactsTel;
	}

	public String getContactsTel() 
	{
		return contactsTel;
	}
	public void setContactsName(String contactsName) 
	{
		this.contactsName = contactsName;
	}

	public String getContactsName() 
	{
		return contactsName;
	}
	public void setFax(String fax) 
	{
		this.fax = fax;
	}

	public String getFax() 
	{
		return fax;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getEmail() 
	{
		return email;
	}
	public void setSettCycle(Integer settCycle) 
	{
		this.settCycle = settCycle;
	}

	public Integer getSettCycle() 
	{
		return settCycle;
	}
	public void setBusiStatus(String busiStatus) 
	{
		this.busiStatus = busiStatus;
	}

	public String getBusiStatus() 
	{
		return busiStatus;
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
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}
	public void setBusinessLicenseImg(String businessLicenseImg) 
	{
		this.businessLicenseImg = businessLicenseImg;
	}

	public String getBusinessLicenseImg() 
	{
		return businessLicenseImg;
	}
	public void setUnifyingSocialCredit(String unifyingSocialCredit) 
	{
		this.unifyingSocialCredit = unifyingSocialCredit;
	}

	public String getUnifyingSocialCredit() 
	{
		return unifyingSocialCredit;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("custName", getCustName())
            .append("custAdd", getCustAdd())
            .append("contactsTel", getContactsTel())
            .append("contactsName", getContactsName())
            .append("fax", getFax())
            .append("email", getEmail())
            .append("settCycle", getSettCycle())
            .append("busiStatus", getBusiStatus())
            .append("createdTime", getCreatedTime())
            .append("createBy", getCreateBy())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("businessLicenseImg", getBusinessLicenseImg())
            .append("unifyingSocialCredit", getUnifyingSocialCredit())
            .toString();
    }
}

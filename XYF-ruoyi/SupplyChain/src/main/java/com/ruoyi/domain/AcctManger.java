package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 账户表 acct_manger
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
public class AcctManger extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 账号 */
	private String accNo;
	/** 账号名 */
	private String accName;
	/** 银行名称 */
	private String bankName;
	/** 银行编号 */
	private String bankNo;
	/** 余额 */
	private BigDecimal balance;
	/** 冻结余额 */
	private BigDecimal freezingBalance;
	/** 可用余额 */
	private BigDecimal canUserBalance;
	/** 用户ID */
	private String userId;
	/** 账户类型 */
	private String accType;
	/** 业务状态 */
	private String busiStatus;
	/** 创建时间 */
	private Date createdTime;
	/** 最后修改时间 */
	private Date updatedTime;
	/** 是否可用（可用=1，禁用=0） */
	private Integer enable;
	/** 身份证号码 */
	private String idCardNo;
	/** 分行名称 */
	private String branchName;
	/** 分行行号 */
	private String branchNo;
	/** 省份 */
	private String provinceName;
	/** 城市 */
	private String cityName;
	/** 用户姓名 */
	private String userName;
	/** 企业ID */
	private Integer custId;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setAccNo(String accNo) 
	{
		this.accNo = accNo;
	}

	public String getAccNo() 
	{
		return accNo;
	}
	public void setAccName(String accName) 
	{
		this.accName = accName;
	}

	public String getAccName() 
	{
		return accName;
	}
	public void setBankName(String bankName) 
	{
		this.bankName = bankName;
	}

	public String getBankName() 
	{
		return bankName;
	}
	public void setBankNo(String bankNo) 
	{
		this.bankNo = bankNo;
	}

	public String getBankNo() 
	{
		return bankNo;
	}
	public void setBalance(BigDecimal balance) 
	{
		this.balance = balance;
	}

	public BigDecimal getBalance() 
	{
		return balance;
	}
	public void setFreezingBalance(BigDecimal freezingBalance) 
	{
		this.freezingBalance = freezingBalance;
	}

	public BigDecimal getFreezingBalance() 
	{
		return freezingBalance;
	}
	public void setCanUserBalance(BigDecimal canUserBalance) 
	{
		this.canUserBalance = canUserBalance;
	}

	public BigDecimal getCanUserBalance() 
	{
		return canUserBalance;
	}
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getUserId() 
	{
		return userId;
	}
	public void setAccType(String accType) 
	{
		this.accType = accType;
	}

	public String getAccType() 
	{
		return accType;
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
	public void setEnable(Integer enable) 
	{
		this.enable = enable;
	}

	public Integer getEnable() 
	{
		return enable;
	}
	public void setIdCardNo(String idCardNo) 
	{
		this.idCardNo = idCardNo;
	}

	public String getIdCardNo() 
	{
		return idCardNo;
	}
	public void setBranchName(String branchName) 
	{
		this.branchName = branchName;
	}

	public String getBranchName() 
	{
		return branchName;
	}
	public void setBranchNo(String branchNo) 
	{
		this.branchNo = branchNo;
	}

	public String getBranchNo() 
	{
		return branchNo;
	}
	public void setProvinceName(String provinceName) 
	{
		this.provinceName = provinceName;
	}

	public String getProvinceName() 
	{
		return provinceName;
	}
	public void setCityName(String cityName) 
	{
		this.cityName = cityName;
	}

	public String getCityName() 
	{
		return cityName;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getUserName() 
	{
		return userName;
	}
	public void setCustId(Integer custId) 
	{
		this.custId = custId;
	}

	public Integer getCustId() 
	{
		return custId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("accNo", getAccNo())
            .append("accName", getAccName())
            .append("bankName", getBankName())
            .append("bankNo", getBankNo())
            .append("balance", getBalance())
            .append("freezingBalance", getFreezingBalance())
            .append("canUserBalance", getCanUserBalance())
            .append("userId", getUserId())
            .append("accType", getAccType())
            .append("busiStatus", getBusiStatus())
            .append("createdTime", getCreatedTime())
            .append("createdBy", getCreatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("enable", getEnable())
            .append("idCardNo", getIdCardNo())
            .append("branchName", getBranchName())
            .append("branchNo", getBranchNo())
            .append("provinceName", getProvinceName())
            .append("cityName", getCityName())
            .append("userName", getUserName())
            .append("custId", getCustId())
            .toString();
    }
}

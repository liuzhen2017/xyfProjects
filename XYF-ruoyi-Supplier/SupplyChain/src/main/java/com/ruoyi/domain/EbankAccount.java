package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.math.BigDecimal;

/**
 * 每个转账账户的虚拟账号表 ebank_account
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
public class EbankAccount extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 每一个用户的账号，记录转账，系统生成 */
	private String accountNo;
	/** 用户编号 */
	private Long userNo;
	/** 用户姓名 */
	private String userName;
	/** 身份证号码 */
	private String idCardNo;
	/** 账号名 */
	private String accountName;
	/** 账户类型 */
	private String accountType;
	/** 银行卡号 */
	private String bankCardNo;
	/** 银行名称 */
	private String bankName;
	/** 银行编号 */
	private String bankNo;
	/** 分行名称 */
	private String branchName;
	/** 分行行号 */
	private String branchNo;
	/** 省份 */
	private String provinceName;
	/** 城市 */
	private String cityName;
	/** 余额 */
	private BigDecimal balance;
	/** 冻结余额 */
	private BigDecimal frozenBalance;
	/** 可用余额 */
	private BigDecimal availableBalance;
	/** 业务状态 */
	private String businessStatus;
	/** 是否可用（可用=true，禁用=false） */
	private Integer enable;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setAccountNo(String accountNo) 
	{
		this.accountNo = accountNo;
	}

	public String getAccountNo() 
	{
		return accountNo;
	}
	public void setUserNo(Long userNo) 
	{
		this.userNo = userNo;
	}

	public Long getUserNo() 
	{
		return userNo;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getUserName() 
	{
		return userName;
	}
	public void setIdCardNo(String idCardNo) 
	{
		this.idCardNo = idCardNo;
	}

	public String getIdCardNo() 
	{
		return idCardNo;
	}
	public void setAccountName(String accountName) 
	{
		this.accountName = accountName;
	}

	public String getAccountName() 
	{
		return accountName;
	}
	public void setAccountType(String accountType) 
	{
		this.accountType = accountType;
	}

	public String getAccountType() 
	{
		return accountType;
	}
	public void setBankCardNo(String bankCardNo) 
	{
		this.bankCardNo = bankCardNo;
	}

	public String getBankCardNo() 
	{
		return bankCardNo;
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
	public void setBalance(BigDecimal balance) 
	{
		this.balance = balance;
	}

	public BigDecimal getBalance() 
	{
		return balance;
	}
	public void setFrozenBalance(BigDecimal frozenBalance) 
	{
		this.frozenBalance = frozenBalance;
	}

	public BigDecimal getFrozenBalance() 
	{
		return frozenBalance;
	}
	public void setAvailableBalance(BigDecimal availableBalance) 
	{
		this.availableBalance = availableBalance;
	}

	public BigDecimal getAvailableBalance() 
	{
		return availableBalance;
	}
	public void setBusinessStatus(String businessStatus) 
	{
		this.businessStatus = businessStatus;
	}

	public String getBusinessStatus() 
	{
		return businessStatus;
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
            .append("accountNo", getAccountNo())
            .append("userNo", getUserNo())
            .append("userName", getUserName())
            .append("idCardNo", getIdCardNo())
            .append("accountName", getAccountName())
            .append("accountType", getAccountType())
            .append("bankCardNo", getBankCardNo())
            .append("bankName", getBankName())
            .append("bankNo", getBankNo())
            .append("branchName", getBranchName())
            .append("branchNo", getBranchNo())
            .append("provinceName", getProvinceName())
            .append("cityName", getCityName())
            .append("balance", getBalance())
            .append("frozenBalance", getFrozenBalance())
            .append("availableBalance", getAvailableBalance())
            .append("businessStatus", getBusinessStatus())
            .append("enable", getEnable())
            .append("createdTime", getCreatedTime())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}

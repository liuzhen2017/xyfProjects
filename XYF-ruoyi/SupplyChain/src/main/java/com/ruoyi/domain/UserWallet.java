package com.ruoyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户钱包表 user_wallet
 * 
 * @author ruoyi
 * @date 2019-08-20
 */
public class UserWallet
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Long id;
	/** 用户编号 */
	private String userNo;
	/** 用户姓名 */
	private String userName;
	/** s_系统,u_用户,g_供应商 */
	private String accountNo;
	/** T00积分钱包,T01现金钱包,T02优惠券钱包 */
	private String accountType;
	/** U00系统用户,U01 APP用户,U02 供应商 */
	private String userType;
	/** 余额 */
	private BigDecimal balance;
	/** 冻结余额 */
	private BigDecimal frozenBalance;
	/** 可用余额 */
	private BigDecimal availableBalance;
	/** 业务状态 */
	private String businessStatus;
	/** 是否可用（可用=1，禁用=0） */
	private Integer enable;
	/** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String createTime;
	/** 最后修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String lastModifyTime;

    /** 搜索值 */
    private String searchValue;

    /** 请求参数 */
    private Map<String, Object> params;

    public String getSearchValue()
    {
        return searchValue;
    }

    public void setSearchValue(String searchValue)
    {
        this.searchValue = searchValue;
    }

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setUserNo(String userNo) 
	{
		this.userNo = userNo;
	}

	public String getUserNo() 
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
	public void setAccountNo(String accountNo) 
	{
		this.accountNo = accountNo;
	}

	public String getAccountNo() 
	{
		return accountNo;
	}
	public void setAccountType(String accountType) 
	{
		this.accountType = accountType;
	}

	public String getAccountType() 
	{
		return accountType;
	}
	public void setUserType(String userType) 
	{
		this.userType = userType;
	}

	public String getUserType() 
	{
		return userType;
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
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getCreateTime()
	{
		return createTime;
	}
	public void setLastModifyTime(String lastModifyTime)
	{
		this.lastModifyTime = lastModifyTime;
	}

	public String getLastModifyTime()
	{
		return lastModifyTime;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userNo", getUserNo())
            .append("userName", getUserName())
            .append("accountNo", getAccountNo())
            .append("accountType", getAccountType())
            .append("userType", getUserType())
            .append("balance", getBalance())
            .append("frozenBalance", getFrozenBalance())
            .append("availableBalance", getAvailableBalance())
            .append("businessStatus", getBusinessStatus())
            .append("enable", getEnable())
            .append("createTime", getCreateTime())
            .append("lastModifyTime", getLastModifyTime())
            .toString();
    }
}

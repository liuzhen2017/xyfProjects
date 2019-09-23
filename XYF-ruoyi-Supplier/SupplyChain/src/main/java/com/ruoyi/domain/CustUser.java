package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 用户表 cust_user
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
public class CustUser extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 手机号 */
	private String phone;
	/** 密码 */
	private String password;
	/**  */
	private String payPassword;
	/** 当前用户的推荐码，用于分享给别人，忽略大小写 */
	private String userCode;
	/** 昵称 */
	private String nickName;
	/** 真实姓名 */
	private String name;
	/** 性别, 0女，1男 */
	private Integer sex;
	/** 用户头像 */
	private String photoUrl;
	/** 用户状态，0未激活，1已激活，2已注销，3已封锁 */
	private Integer status;
	/** 用户级别 */
	private Integer level;
	/** 推荐人的推荐码 */
	private String referralCode;
	/** 证件号 */
	private String cardNo;
	/** 证件类型 */
	private String cardType;
	/**  */
	private Integer authStatus;
	/**  */
	private Date authTime;
	/** 微信号码 */
	private String wechat;
	/** 对接微信等第三方登陆 */
	private String openId;
	/**  */
	private String unionId;
	/** 用户来源 */
	private String dataSource;
	/** 备注信息 */
	private String remarks;
	/**  */
	private Long userNo;
	/**  */
	private Long referrerNo;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setPhone(String phone) 
	{
		this.phone = phone;
	}

	public String getPhone() 
	{
		return phone;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}

	public String getPassword() 
	{
		return password;
	}
	public void setPayPassword(String payPassword) 
	{
		this.payPassword = payPassword;
	}

	public String getPayPassword() 
	{
		return payPassword;
	}
	public void setUserCode(String userCode) 
	{
		this.userCode = userCode;
	}

	public String getUserCode() 
	{
		return userCode;
	}
	public void setNickName(String nickName) 
	{
		this.nickName = nickName;
	}

	public String getNickName() 
	{
		return nickName;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setSex(Integer sex) 
	{
		this.sex = sex;
	}

	public Integer getSex() 
	{
		return sex;
	}
	public void setPhotoUrl(String photoUrl) 
	{
		this.photoUrl = photoUrl;
	}

	public String getPhotoUrl() 
	{
		return photoUrl;
	}
	public void setStatus(Integer status) 
	{
		this.status = status;
	}

	public Integer getStatus() 
	{
		return status;
	}
	public void setLevel(Integer level) 
	{
		this.level = level;
	}

	public Integer getLevel() 
	{
		return level;
	}
	public void setReferralCode(String referralCode) 
	{
		this.referralCode = referralCode;
	}

	public String getReferralCode() 
	{
		return referralCode;
	}
	public void setCardNo(String cardNo) 
	{
		this.cardNo = cardNo;
	}

	public String getCardNo() 
	{
		return cardNo;
	}
	public void setCardType(String cardType) 
	{
		this.cardType = cardType;
	}

	public String getCardType() 
	{
		return cardType;
	}
	public void setAuthStatus(Integer authStatus) 
	{
		this.authStatus = authStatus;
	}

	public Integer getAuthStatus() 
	{
		return authStatus;
	}
	public void setAuthTime(Date authTime) 
	{
		this.authTime = authTime;
	}

	public Date getAuthTime() 
	{
		return authTime;
	}
	public void setWechat(String wechat) 
	{
		this.wechat = wechat;
	}

	public String getWechat() 
	{
		return wechat;
	}
	public void setOpenId(String openId) 
	{
		this.openId = openId;
	}

	public String getOpenId() 
	{
		return openId;
	}
	public void setUnionId(String unionId) 
	{
		this.unionId = unionId;
	}

	public String getUnionId() 
	{
		return unionId;
	}
	public void setDataSource(String dataSource) 
	{
		this.dataSource = dataSource;
	}

	public String getDataSource() 
	{
		return dataSource;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
	}
	public void setUserNo(Long userNo) 
	{
		this.userNo = userNo;
	}

	public Long getUserNo() 
	{
		return userNo;
	}
	public void setReferrerNo(Long referrerNo) 
	{
		this.referrerNo = referrerNo;
	}

	public Long getReferrerNo() 
	{
		return referrerNo;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("phone", getPhone())
            .append("password", getPassword())
            .append("payPassword", getPayPassword())
            .append("userCode", getUserCode())
            .append("nickName", getNickName())
            .append("name", getName())
            .append("sex", getSex())
            .append("photoUrl", getPhotoUrl())
            .append("status", getStatus())
            .append("level", getLevel())
            .append("referralCode", getReferralCode())
            .append("cardNo", getCardNo())
            .append("cardType", getCardType())
            .append("authStatus", getAuthStatus())
            .append("authTime", getAuthTime())
            .append("wechat", getWechat())
            .append("openId", getOpenId())
            .append("unionId", getUnionId())
            .append("dataSource", getDataSource())
            .append("remarks", getRemarks())
            .append("createdTime", getCreatedTime())
            .append("updatedTime", getUpdatedTime())
            .append("userNo", getUserNo())
            .append("referrerNo", getReferrerNo())
            .toString();
    }
}

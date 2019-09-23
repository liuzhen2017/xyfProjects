package com.ruoyi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 用户表 sys_user
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 用户ID */
	private Long userId;
	/** 部门ID */
	private Long deptId;
	/** 登录账号 */
	private String loginName;
	/** 用户昵称 */
	private String userName;
	/** 用户类型（00系统用户） */
	private String userType;
	/** 用户邮箱 */
	private String email;
	/** 手机号码 */
	private String phonenumber;
	/** 用户性别（0男 1女 2未知） */
	private String sex;
	/** 头像路径 */
	private String avatar;
	/** 密码 */
	private String password;
	/** 盐加密 */
	private String salt;
	/** 帐号状态（0正常 1停用） */
	private String status;
	/** 删除标志（0代表存在 2代表删除） */
	private String delFlag;
	/** 最后登陆IP */
	private String loginIp;
	/** 最后登陆时间 */
	private Date loginDate;
	/** 备注 */
	private String remarks;
	/** 企业编号 */
	private String custNo;
	/** 企业类型 */
	private String custType;

	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}

	public Long getUserId() 
	{
		return userId;
	}
	public void setDeptId(Long deptId) 
	{
		this.deptId = deptId;
	}

	public Long getDeptId() 
	{
		return deptId;
	}
	public void setLoginName(String loginName) 
	{
		this.loginName = loginName;
	}

	public String getLoginName() 
	{
		return loginName;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getUserName() 
	{
		return userName;
	}
	public void setUserType(String userType) 
	{
		this.userType = userType;
	}

	public String getUserType() 
	{
		return userType;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getEmail() 
	{
		return email;
	}
	public void setPhonenumber(String phonenumber) 
	{
		this.phonenumber = phonenumber;
	}

	public String getPhonenumber() 
	{
		return phonenumber;
	}
	public void setSex(String sex) 
	{
		this.sex = sex;
	}

	public String getSex() 
	{
		return sex;
	}
	public void setAvatar(String avatar) 
	{
		this.avatar = avatar;
	}

	public String getAvatar() 
	{
		return avatar;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}

	public String getPassword() 
	{
		return password;
	}
	public void setSalt(String salt) 
	{
		this.salt = salt;
	}

	public String getSalt() 
	{
		return salt;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
	}
	public void setDelFlag(String delFlag) 
	{
		this.delFlag = delFlag;
	}

	public String getDelFlag() 
	{
		return delFlag;
	}
	public void setLoginIp(String loginIp) 
	{
		this.loginIp = loginIp;
	}

	public String getLoginIp() 
	{
		return loginIp;
	}
	public void setLoginDate(Date loginDate) 
	{
		this.loginDate = loginDate;
	}

	public Date getLoginDate() 
	{
		return loginDate;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
	}
	public void setCustNo(String custNo) 
	{
		this.custNo = custNo;
	}

	public String getCustNo() 
	{
		return custNo;
	}
	public void setCustType(String custType) 
	{
		this.custType = custType;
	}

	public String getCustType() 
	{
		return custType;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("loginName", getLoginName())
            .append("userName", getUserName())
            .append("userType", getUserType())
            .append("email", getEmail())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("avatar", getAvatar())
            .append("password", getPassword())
            .append("salt", getSalt())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("remarks", getRemarks())
            .append("custNo", getCustNo())
            .append("custType", getCustType())
            .toString();
    }
}

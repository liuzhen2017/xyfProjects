package com.xinyunfu.report.model;

import lombok.*;
import org.apache.commons.lang3.builder.*;

import java.util.*;

/**
 * 用户表 cust_user
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
@Data
public class CustUser
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
    /**创建时间*/
	private String createTime;
	/**用户注册量*/
	private Double registNum;
	//查询开始时间
	private  String startDate;
	//查询结束时间
	private  String endDate;
}

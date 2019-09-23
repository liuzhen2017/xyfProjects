package com.xinyunfu.customer.dto.user;

import com.xinyunfu.customer.constant.UserConstants;
import com.xinyunfu.customer.domain.user.CustUserDTO;
import com.xinyunfu.customer.domain.user.CustUserExtensionDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 用户信息 + 用户扩展
 */
@Data
@NoArgsConstructor
public class CustUserWithExtensionDTO {
    private Long id;
    private Long userNo;            //用户编号
    private String phone;           //手机号
    private String password;        //密码
    private String payPassword;     //支付密码
    private String userCode;        //当前用户的推荐码
    private String nickName;        //昵称
    private String name;            //真实姓名
    private Integer sex;            //性别
    private String photoUrl;        //用户头像
    private Integer status;         //用户状态
    private Integer level;          //用户级别
    private Long referrerNo;        //推荐人id
    private String referralCode;    //推荐人推荐码
    private String cardNo;          //证件号
    private String cardType;        //证件类型
    private Integer authStatus;     //认证状态
    private Timestamp authTime;     //认证成功时间
    private String wechat;          //微信号
    private String openId;          //对接微信等第三方登陆
    private String unionId;         //对接微信等第三方登陆
    private String dataSource;      //用户来源
    private String remark;          //备注信息
    private Timestamp createTime;      //创建时间
    private Timestamp lastModifyTime;  //最后修改时间

    private Integer mealNu;                 //套餐购买数量

    public CustUserWithExtensionDTO(CustUserDTO user, CustUserExtensionDTO extension){
        this.id = user.getId();
        this.userNo = user.getUserNo();
        this.phone = user.getPhone();
        this.password = user.getPassword();
        this.payPassword = user.getPayPassword();
        this.userCode = user.getUserCode();
        this.nickName = user.getNickName();
        this.name = user.getName();
        this.sex = user.getSex();
        this.photoUrl = user.getPhotoUrl();
        this.status = user.getStatus();
        this.level = user.getLevel();
        this.referrerNo = user.getReferrerNo();
        this.referralCode = user.getReferralCode();
        this.cardNo = user.getCardNo();
        this.cardType = user.getCardType();
        this.authStatus = user.getAuthStatus();
        this.authTime = user.getAuthTime();
        this.wechat = user.getWechat();
        this.openId = user.getOpenId();
        this.unionId = user.getUnionId();
        this.dataSource = user.getDataSource();
        this.remark = user.getRemark();
        this.createTime = user.getCreateTime();
        this.lastModifyTime = user.getLastModifyTime();

        this.mealNu = 0;
        if(!Objects.isNull(extension)) this.mealNu = extension.getMealNu();
        if(this.mealNu>0 && this.level==UserConstants.level_0) this.level = UserConstants.level_2;
    }
}

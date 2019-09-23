package com.xinyunfu.customer.domain.user;

import com.xinyunfu.customer.constant.UserConstants;
import com.xinyunfu.customer.dto.user.UserCardImageURL;
import com.xinyunfu.customer.dto.user.UserRegisterDTO;
import com.xinyunfu.customer.utils.EncryptionTools;
import com.xinyunfu.customer.utils.SnowFlake;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 用户信息, 详情查看UserConstants
 * @see com.xinyunfu.customer.constant.UserConstants
 */
@NoArgsConstructor
@Data
public class CustUserDTO {

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

    public CustUserDTO(UserRegisterDTO register, CustUserDTO refUser, String userCode){
        if(Objects.isNull(refUser)) refUser = new CustUserDTO();
        this.userNo = SnowFlake.nextId();
        while(true){
            if(this.userNo == UserConstants.system_user_no)     //用户编号不能是19个8的编号
                this.userNo = SnowFlake.nextId();
            else
                break;
        }
        this.password = EncryptionTools.md5(register.getPassword(), UserConstants.PASSWORD_SALT);
        this.nickName = "用户" + register.getPhone();
        this.userCode = userCode;
        this.phone = register.getPhone();
        this.sex = UserConstants.sex_2;
        this.status = UserConstants.status_1;       //默认已激活
        this.level = UserConstants.level_0;
        this.dataSource = UserConstants.source_0;
        this.referrerNo = refUser.getUserNo();
        this.referralCode = refUser.getUserCode();
    }
}

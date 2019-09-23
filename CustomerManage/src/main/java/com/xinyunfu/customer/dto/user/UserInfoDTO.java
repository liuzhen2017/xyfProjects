package com.xinyunfu.customer.dto.user;

import com.xinyunfu.customer.domain.user.CustUserDTO;
import com.xinyunfu.customer.utils.FuzzyUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@NoArgsConstructor
@Data
public class UserInfoDTO {
    private Long id;
    private Long userNo;            //用户编号
    private String nickName;        //昵称
    private String userCode;        //当前用户的推荐码
    private String name;            //真实姓名
    private String cardNo;          //证件号
    private String phone;           //手机号
    private Integer sex;            //性别
    private String photoUrl;        //用户头像
    private Integer status;         //用户状态
    private Integer level;          //用户级别
    private Long referrerNo;        //推荐人id
    private String referralCode;    //推荐人推荐码
    private Integer authStatus;     //证件状态
    private String wechat;          //微信号
    private String dataSource;      //用户来源
    private String remark;          //备注信息

    private Boolean hasPayPassword; //用户是否已设置支付密码
    private Boolean hasPassword;    //是否设置登陆密码
    private Boolean firstLogin;     //用户是否是首次登陆
    private String createTime;     //创建时间

    private Integer mealNu;         //购买套餐数量

    public UserInfoDTO(CustUserWithExtensionDTO user, Boolean fuzzy){
        this.id = user.getId();
        this.userNo = user.getUserNo();
        this.nickName = user.getNickName();
        this.userCode = user.getUserCode();
        this.name = fuzzy?FuzzyUtil.nameFuzzy(user.getName()): user.getName();
        this.cardNo = fuzzy?FuzzyUtil.cardFuzzy(user.getCardNo()):user.getCardNo();
        this.phone = user.getPhone();
        this.sex = user.getSex();
        this.photoUrl = user.getPhotoUrl();
        this.status = user.getStatus();
        this.level = user.getLevel();
        this.referrerNo = user.getReferrerNo();
        this.referralCode = user.getReferralCode();
        this.authStatus = user.getAuthStatus();
        this.wechat = fuzzy?FuzzyUtil.fuzzy(user.getWechat()):user.getWechat();
        this.dataSource = user.getDataSource();
        this.remark = user.getRemark();
        if(Objects.nonNull(user.getCreateTime())) {
            this.createTime = ZonedDateTime.ofInstant(user.getCreateTime().toInstant(), ZoneId.of("+8"))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        this.hasPayPassword = Objects.nonNull(user.getPayPassword());
        this.hasPassword = Objects.nonNull(user.getPassword());
        this.firstLogin = false;

        this.mealNu = user.getMealNu();
    }
    public UserInfoDTO(CustUserDTO user, Boolean fuzzy){
        this.id = user.getId();
        this.userNo = user.getUserNo();
        this.nickName = user.getNickName();
        this.userCode = user.getUserCode();
        this.name = fuzzy?FuzzyUtil.nameFuzzy(user.getName()): user.getName();
        this.cardNo = fuzzy?FuzzyUtil.cardFuzzy(user.getCardNo()):user.getCardNo();
        this.phone = user.getPhone();
        this.sex = user.getSex();
        this.photoUrl = user.getPhotoUrl();
        this.status = user.getStatus();
        this.level = user.getLevel();
        this.referrerNo = user.getReferrerNo();
        this.referralCode = user.getReferralCode();
        this.authStatus = user.getAuthStatus();
        this.wechat = fuzzy?FuzzyUtil.fuzzy(user.getWechat()):user.getWechat();
        this.dataSource = user.getDataSource();
        this.remark = user.getRemark();
        if(Objects.nonNull(user.getCreateTime())) {
            this.createTime = ZonedDateTime.ofInstant(user.getCreateTime().toInstant(), ZoneId.of("+8"))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        this.hasPayPassword = Objects.nonNull(user.getPayPassword());
        this.hasPassword = Objects.nonNull(user.getPassword());
        this.firstLogin = false;

        this.mealNu = 0;
    }
}

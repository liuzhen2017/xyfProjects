package com.xinyunfu.customer.exception;
import com.xinyunfu.customer.constant.LogTypeConstant;
import lombok.Getter;

public enum CustomerExceptionEnum {

    /**  用户相关错误码 1000 -- 1100 */
    USER_LOGIN_NOUSER(1000, "用户名或密码错误"),
    USER_LOGIN_PASSWORD(1001, "用户名或密码错误"),
    USER_LOGIN_VERIFY_CODE(1002, "验证码不存在或已失效"),
    USER_LOGIN_HASUSER(1003, "用户已存在"),
    USER_PASSWORD_CONFIRM(1004, "两次输入的密码不一致"),
    USER_PAY_PASSWORD_SAME(1005, "输入的新密码与旧密码相同"),
    USER_PAY_PASSWORD_OLD(1006, "旧密码错误"),
    USER_REGISTER_CODE(1007, "推荐码不存在"),
    USER_REGISTER_PHONE(1008, "该手机号已被注册"),
    USER_REGISTER_TOKEN(1009, "TOKEN已过期"),
    USER_OFTEN_LOGIN(1010, "登陆失败次数太多，请稍后登陆~"),
    USER_PAY_PASSWORD_ERROR(1011, "支付密码错误"),
    USER_REGISTER_NON_PHONE(1012, "该手机号未被注册"),
    USER_EXTENSION_NON_PHONE(1013, "用户不存在"),

    /** 通用接口错误码 1101 -- 1200 */
    COMMON_IAMGE_CARD_ERROR(1101, "身份证图片解析失败"),
    COMMON_IAMGE_BANK_ERROR(1102, "银行卡图片解析失败"),
    COMMON_CHECK_TOKEN_ERROR(1103, "token校验失败"),
    COMMON_SEND_VERIFY_CODE(1104, "短信发送失败"),

    /** 认证相关校验码 1201 -- 1300 */
    AUTH_ID_CARD_CONTAINS(1201, "该用户已经通过认证，无需再次认证", LogTypeConstant.ID_CARD_AUTH),
    AUTH_ID_CARD_NO(1202, "当前身份证号已被实名认证过", LogTypeConstant.ID_CARD_AUTH),
    AUTH_ID_CARD_NO_AUTH(1203, "当前用户未完成实名认证", LogTypeConstant.BANK_CARD_AUTH),
    AUTH_BANK_CARD_EXISTS(1204, "该银行卡已存在", LogTypeConstant.BANK_CARD_AUTH),
    AUTH_BANK_NOT_EXISTS(1205, "选中的银行不存在", LogTypeConstant.BANK_CARD_AUTH),
    AUTH_AREA_NOT_EXISTS(1206, "选中的地区不存在", LogTypeConstant.BANK_CARD_AUTH),
    AUTH_BANK_NAME_ERROR(1207, "银行信息错误", LogTypeConstant.BANK_CARD_AUTH),
    AUTH_BANK_MORE_CARD_ERROR(1208, "一个用户只能绑定一张银行卡", LogTypeConstant.BANK_CARD_AUTH),
    AUTH_ID_CARD_ERROR(1209, "银行卡信息与实名信息不匹配，请重新提交！", LogTypeConstant.ID_CARD_AUTH),
    AUTH_BANK_CARD_ERROR(1210, "银行卡信息与实名信息不匹配，请重新提交！", LogTypeConstant.BANK_CARD_AUTH),
    AUTH_CARD_TYPE_ERROR(1211, "不支持的卡类型，请绑定普通借记卡！", LogTypeConstant.BANK_CARD_AUTH),

    FEEDBACK_ERROR(1300, "亲，您今日已经反馈过了哦~"),

    /** 添加收货地址 */
    SHIPPING_ADDRESS_ADD_ERROR(1300, "添加收货地址失败，收货地址最多有10个"),

    COMMON(9999, "未知错误");

    @Getter private Integer errCode;    //错误码
    @Getter private String desc;        //错误描述
    @Getter private Boolean saveLog;    //是否保存日志
    @Getter private String logType;     //保存的日志类型
    @Getter private String logTemp;     //保存的日志模板
    @Getter private Object[] logData;   //往日志模板中插入的值
    @Getter private Long userNo;        //用户编号

    CustomerExceptionEnum(Integer errCode, String desc){
        this.errCode = errCode;
        this.desc = desc;
        this.saveLog = false;
    }

    CustomerExceptionEnum(Integer errCode, String desc, String logType) {
        this.errCode = errCode;
        this.desc = desc;
        this.saveLog = true;
        this.logType = logType;
        this.logTemp = LogTypeConstant.logTypeRelation.get(logType);
    }

    public CustomerExceptionEnum setDataData(Object... obj){
        this.logData = obj;
        return this;
    }
    public CustomerExceptionEnum setUserNo(Long userNo){
        this.userNo = userNo;
        return this;
    }

    public CustomerExceptionEnum setDesc(String desc){
        this.desc = desc;
        return this;
    }
}

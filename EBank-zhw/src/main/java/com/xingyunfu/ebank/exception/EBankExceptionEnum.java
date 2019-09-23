package com.xingyunfu.ebank.exception;

import lombok.Getter;
public enum EBankExceptionEnum {


    /** 账户 2000 -- 2099 */
    ACCOUNT_ADD_HAVE_SAVE_USER(2000, "该账户已存在"),
    ACCOUNT_ADD_NO_USER(2001, "无法创建账户，用户编号不存在"),
    ACCOUNT_ONE_MORE_SYSTEM_ACCOUNT(2002, "系统存在超过两个系统账户，服务无法正常运行"),

    /** 用户间转账 2100 -- 2199 */
    TRANSFER_ADD_ORDER_NO(2100, "该订单已被创建"),
    TRANSFER_APPLY_ORDER_NO(2101, "发起支付请求失败"),
    TRANSFER_APPLY_VERIFY_ERROR(2102, "发起支付请求验证签证失败"),
    TRANSFER_APPLY_RESPONSE(2103, "支付中心响应错误内容"),

    /** 产品购买 2200 -- 2299 */
    PRODUCT_ADD_STATUS_PROCESS(2200, "该订单已存在，这正在处理支付"),
    PRODUCT_ADD_STATUS_SUCCESS(2201, "该订单已存在，已经申请支付成功"),
    PRODUCT_ADD_STATUS_FAILD(2202, "该订单已存在，支付失败"),
    PRODUCT_ADD_RESPONSE_ERROR(2203, "申请支付失败"),
    PRODUCT_ADD_VERIFY_ERROR(2204, "验证签证失败"),
    PRODUCT_ADD_RESPONSE(2205, "支付中心响应错误内容"),
    PRODUCT_CHANNEL_ERROR(2206, "支付渠道不存在或被关闭"),
    PRODUCT_ADD_RESPONSE_WALLET_ERROR(2207, "申请支付失败"),
    PRODUCT_ORDER_NONE(2208, "不存在该订单"),
    PRODUCT_FAST_PAYMENT_CARD(2209, "快捷支付需要绑定银行卡"),

    /** 支付渠道管理 */
    CHANNEL_UPDATE_ERROR(2300, "支付渠道无法修改，支付渠道错误"),

    COMMON(9999, "未知错误");

    @Getter private Integer errCode;    //错误码
    @Getter private String desc;        //错误描述
    @Getter private Boolean saveLog;    //是否保存日志
    @Getter private String logType;     //保存的日志类型
    @Getter private String logTemp;     //保存的日志模板
    @Getter private Object[] logData;   //往日志模板中插入的值
    @Getter private Long userNo;        //用户编号

    EBankExceptionEnum(Integer errCode, String desc){
        this.errCode = errCode;
        this.desc = desc;
        this.saveLog = false;
    }

    EBankExceptionEnum(Integer errCode, String desc, String logType) {
        this.errCode = errCode;
        this.desc = desc;
        this.saveLog = true;
        this.logType = logType;
//        this.logTemp = LogTypeConstant.logTypeRelation.get(logType);
    }

    public EBankExceptionEnum setDesc(String desc){
        this.desc = desc;
        return this;
    }

    public EBankExceptionEnum setDataData(Object... obj){
        this.logData = obj;
        return this;
    }
    public EBankExceptionEnum setUserNo(Long userNo){
        this.userNo = userNo;
        return this;
    }
}

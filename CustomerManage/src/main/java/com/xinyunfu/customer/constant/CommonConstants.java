package com.xinyunfu.customer.constant;

public interface CommonConstants {
    String header_uid = "currentUserId";
    String header_ip = "X-Real-IP";

    /** 发送验证码的类型 */
    String LOGIN = "login";
    String REGISTER = "register";
    String RESET_PAY_PASSWORD = "resetPayPassword";
    String RESET_PASSWORD = "resetPassword";
    String BIND_PHONE = "bindPhone";
    String UNBIND_PHONE = "unbindPhone";

    /** 图片认证时的图片类型 */
    String ID_CARD = "idCard";
    String BANK_CARD = "bankCard";

    /** 手机类型，ios/android */
    String phoneType_ios = "ios";
    String phoneType_android = "android";

    /** 图形验证码验证类型 */
    Integer tencent = 0;
    Integer dingxiang = 1;
}

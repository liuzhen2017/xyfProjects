package com.xinyunfu.customer.constant;

import java.util.HashMap;
import java.util.Map;

public interface LogTypeConstant {

    /** 身份证认证 */
    String ID_CARD_AUTH = "ID_CARD_AUTH";
    String temp_0 = "身份证认证失败，用户编号：%s，身份证号：%s";
    /** 银行卡绑定 */
    String BANK_CARD_AUTH = "BANK_CARD_AUTH";
    String temp_1 = "银行卡认证失败，用户编号：%s，银行卡号：%s";
    /** 记录用户登陆 */
    String USER_LOGIN_IN = "USER_LOGIN_IN";
    String temp_2 = "用户登陆成功，用户编号：%s";

    Map<String, String> logTypeRelation = new HashMap<String, String>(){{
        put(ID_CARD_AUTH, temp_0);
        put(BANK_CARD_AUTH, temp_1);
        put(USER_LOGIN_IN, temp_2);
    }};
}

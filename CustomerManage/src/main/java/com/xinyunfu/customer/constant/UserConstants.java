package com.xinyunfu.customer.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * 用户信息常量配置
 */
public interface UserConstants {

    Long system_user_no = 8888888888888888888L;

    BiFunction<String, String, I18N> biFun = (var0, var1) -> new I18N(var0, var1);

    String PASSWORD_SALT = "852fc975-ecde-4fbe-b157-b396fe9e34d9";

    /** 用户性别 */
    Integer sex_0 = 0, sex_1 = 1, sex_2 = 2;
    Map<Integer, I18N> USER_SEX = new HashMap<Integer, I18N>(){{
        put(sex_0, biFun.apply("woman", "女"));
        put(sex_1, biFun.apply("man", "男"));
        put(sex_2, biFun.apply("unknow", "未知"));
    }};

    /** 用户状态 */
    Integer status_0 = 0, status_1 = 1, status_2 = 2, status_3 = 3;
    Map<Integer, I18N> USER_STATUS = new HashMap<Integer, I18N>(){{
        put(status_0, biFun.apply("not active", "未激活"));
        put(status_1, biFun.apply("activated", "已激活"));
        put(status_2, biFun.apply("write-off", "已注销"));
        put(status_3, biFun.apply("blockaded", "已封锁"));
    }};

    /** 用户级别 */
    Integer level_0 = 0, level_1 = 1, level_2 = 2;
    Map<Integer, I18N> USER_LEVEL = new HashMap<Integer, I18N>(){{
        put(level_0, biFun.apply("common", "普通"));
        put(level_1, biFun.apply("大使", "大使"));
        put(level_2, biFun.apply("VIP", "VIP"));
    }};

    /** 证件类型 */
    String type_0 = "id_card", type_1 = "", type_2 = "";
    Map<String, I18N> CARD_TYPE = new HashMap<String, I18N>(){{
        put(type_0, biFun.apply("id card", "身份证"));
        put(type_1, biFun.apply("", ""));
        put(type_2, biFun.apply("", ""));
    }};

    /** 认证状态 */
    Integer aStatus_0 = 0, aStatus_1 = 1;
    Map<Integer, I18N> AUTH_STATUS = new HashMap<Integer, I18N>(){{
        put(aStatus_0, biFun.apply("fail", "认证失败"));
        put(aStatus_1, biFun.apply("success", "认证通过"));
    }};

    /** 用户来源 */
    String source_0 = "", source_1 = "", source_2 = "";
    Map<String, I18N> DATA_SOURCE = new HashMap<String, I18N>(){{
        put(source_0, biFun.apply("", ""));
        put(source_1, biFun.apply("", ""));
        put(source_2, biFun.apply("", ""));
    }};

    /** 用户注册时调用券集市参数，注册regist，推荐recommend */
    String regist = "regist";
    String recommend = "recommend";
}

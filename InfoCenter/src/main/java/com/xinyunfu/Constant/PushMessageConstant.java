package com.xinyunfu.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 推送配置
 */
public interface PushMessageConstant {

    /** 推送类型，根据类型找到对应的模板和模板编号 */
    String pushType_0 = "shortMessage";     //短信
    String pushType_1 = "pushMessage";      //推送
    String pushType_2 = "wechatMessage";    //微信

    /** android/ios*/
    String phoneType_0 = "android";
    String phoneType_1 = "ios";
    String phoneType_2 = "window";
    String phoneType_3 = "web";

    Map<String, Integer> phoneTypeMap = new HashMap<String, Integer>(){{
        put(phoneType_3, 0);
        put(phoneType_0, 1);
        put(phoneType_1, 2);
    }};

}

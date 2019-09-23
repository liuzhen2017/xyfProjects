package com.xingyunfu.ebank.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付渠道常量
 */
public interface ChannelConstants {
    /** 支付方式 payType */
    Integer payType_0 = 2;      //云闪付
    Integer payType_1 = 16;     //微信支付
    Integer payType_2 = 32;     //支付宝
    Integer payType_3 = 64;     //快捷支付
    Integer payType_4 = 128;    //网银支付
    Integer payType_5 = -1;     //钱包支付 -- 内部支付渠道

    /** 付款方式tradeType */
    Integer tradeType_0 = 1;    //二维码
    Integer tradeType_1 = 2;    //jsapi
    Integer tradeType_2 = 4;    //h5
    Integer tradeType_3 = 8;    //app
    Integer tradeType_4 = 16;   //付款码

    Map<Integer, String> payTypeMap = new HashMap<Integer, String>(){{
        put(payType_0, "云闪付"); put(payType_1, "微信支付"); put(payType_2, "支付宝");
        put(payType_3, "快捷支付"); put(payType_4, "网银支付"); put(payType_5, "钱包支付");
    }};

    Map<Integer, String> tradeTypeMap = new HashMap<Integer, String>(){{
        put(tradeType_0, "二维码"); put(tradeType_1, "JSAPI"); put(tradeType_2, "H5");
        put(tradeType_3, "APP"); put(tradeType_4, "付款码");
    }};
}

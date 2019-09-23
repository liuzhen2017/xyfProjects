package com.xinyunfu.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XRZ
 * @date 2019/9/10
 * @Description : 售后相关常量
 */
public interface AfterSalerConstant {


    /**
     *  申请类型    为收货，申请退款
     */
    int APPLY_TYPE_REFUND = 0;
    /**
     *  申请类型    已收货，申请售后
     */
    int APPLY_TYPE_AFTERSALER = 1;


    /**
     *  售后类型 换货
     */
    int SALES_TYPE_EXCHANGE = 0;
    /**
     *  售后类型 退货退款
     */
    int SALES_TYPE_RETURN_REFUND = 1;
    /**
     *  售后类型 退款
     */
    int SALES_TYPE_REFUND = 2;
    /**
     *  售后类型
     */
    Map  SALES_TYPE = new HashMap<Integer, String>(){{
        put(0,"换货");
        put(1,"退货退款");
        put(2,"退款");
    }};


    /**
     * 货物状态 已收货
     */
    int GOODS_TYPE_RECEIVED = 0;
    /**
     * 货物状态 未收货
     */
    int GOODS_TYPE_UNRECEIVED = 1;
    /**
     *  货物状态
     */
    Map  GOODS_TYPE = new HashMap<Integer, String>(){{
        put(0,"已收货");
        put(1,"未收货");
    }};


    /**
     * 原因类型 拍错/多拍/不喜欢
     */
    int WHY_YTPE_0 = 0;
    /**
     * 原因类型 商品损坏
     */
    int WHY_TYPE_1 = 1;
    /**
     * 原因类型 商品与描述不符
     */
    int WHY_TYPE_2 = 2;
    /**
     * 原因类型 颜色/款式/图案与描述不符
     */
    int WHY_TYPE_3 = 3;
    /**
     * 原因类型 卖家发错货
     */
    int WHY_TYPE_4 = 4;
    /**
     * 原因类型 其他
     */
    int WHY_TYPE_5 = 5;
    /**
     *  原因类型
     */
    Map  WHY_TYPE = new HashMap<Integer, String>(){{
        put(0,"拍错/多拍/不喜欢");
        put(1,"商品损坏");
        put(2,"商品与描述不符");
        put(3,"颜色/款式/图案与描述不符");
        put(4,"卖家发错货");
        put(5,"其他");
    }};


    /**
     * 处理状态 待处理
     */
    int STATE_0 = 0;
    /**
     * 处理状态 商家处理中
     */
    int STATE_1 = 1;
    /**
     * 处理状态 退款成功
     */
    int STATE_2 = 2;
    /**
     * 处理状态 换货成功
     */
    int STATE_3 = 3;
    /**
     * 处理状态 退货退款成功
     */
    int STATE_4 = 4;
    /**
     * 处理状态 退款失败
     */
    int STATE_5 = 5;
    /**
     * 处理状态 退款关闭
     */
    int STATE_6 = 6;
    /**
     * 处理状态 退货退款失败
     */
    int STATE_7 = 7;
    /**
     * 处理状态 换货失败
     */
    int STATE_8 = 8;
    /**
     * 处理状态
     */
    Map STATE = new HashMap<Integer,String>(){{
        put(0,"待处理");
        put(1,"商家处理中");
        put(2,"退款成功");
        put(3,"换货成功");
        put(4,"退货退款成功");
        put(5,"退款失败");
        put(6,"退款关闭");
        put(7,"退货退款失败");
        put(8,"换货失败");
    }};


    /**
     * 处理结果 同意
     */
    int RESUTL_AGREE = 0;

    /**
     * 处理结果 拒绝
     */
    int RESUTL_REFUSE = 1;


















}

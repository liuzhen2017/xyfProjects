package com.xinyunfu.constant;

/**
 * @author XRZ
 * @date 2019/7/8 0008
 * @Description : 订单常量类
 */
public interface Common {

    /**
     * 启用 表数据
     */
     int ENABLE = 1;

    /**
     * 禁用 表数据
     */
     int DISABLE = 0;


    /**
     * 服务名字
     */
    String SERVICE_NAME = "OrderManage";

    /**
     * 用户ID的KEY
     */
    String UID = "currentUserId";




    /**
     * 购买商品的订单
     */
    String COMMODITY = "00";

    /**
     * 购买套餐的订单
     */
    String PACKAGE = "01";

    /**
     * 购买优惠券的订单
     */
    String COUPONS = "02";



    /**
     * 订单支付类型（主订单）
     */
    int MASTER = 0;

    /**
     * 订单支付类型（子订单）
     */
    int ITEM = 1;

    /**
     * 订单支付的类型
     */
    int PAY_COUPONS = 2;


    /**
     * 购买优惠券的时间限制
     */
    int TIME_COUPON = 0;

    /**
     * 下单时间的限制
     */
    int TIME_ORDER = 1;

    /**
     * 京东商品下单时间的限制
     */
    int ITME_JD = 2;

    /**
     * 结算状态 初始化
     */
    int SETTLEMENT_INIT = 0;

    /**
     * 结算状态 可结算
     */
    int SETTLEMENT_YES = 1;

    /**
     * 结算状态 已结算
     */
    int SETTLEMENT_COMPLETED = 2;

//    /**
//     * 订单支付类型（主订单）
//     */
//    String MASTER = "00";
//
//    /**
//     * 订单支付类型（子订单）
//     */
//    String ITEM = "10";
//
//    /**
//     * 订单支付类型（套餐主订单）
//     */
//    String MASTER_PACKAGE = "01";
//
//    /**
//     * 订单支付类型（套餐子订单）
//     */
//    String ITEM_PACKAGE = "11";



//    /**
//     * 数据是否有效
//     */
//    public static final String[] ENABLES = {"禁用","可用"};
//    /**
//     * 价格清单的价格类型
//     */
//    public static final String[] PRICE_TYPE = {"商品","优惠卷","邮费"};
//    /**
//     * 支付方式
//     */
//    public static final String[] PAY_TYPE = {"微信","支付宝","银联","余额"};
}

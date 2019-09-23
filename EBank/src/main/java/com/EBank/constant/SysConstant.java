package com.EBank.constant;

/**
 * 常量区域
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/8
 */
public class SysConstant {
    public final static String sys_success ="0000";
    /**
     * 订单管理
     */
    public final static String channel_order="OrderManage";
    /**
     * 订单树
     */
    public final static String channel_tree="OrderTree";
    /**
     * 普通用户
     */
    public final static String userType_1 ="1";
    /**
     * 推广大使
     */
    public final static String userType_2 ="2";
    /**
     * 供应链用户
     */
    public final static String userType_3 ="3";
    /**
     * 平台用户
     */
    public final static String userType_4 ="4";


    /**
     * 内部账户最大值->1亿
     */
    public final static String inner_no_max="000000001";

    /**
     * 出账
     */
    public final static String acctranType_1="1";

    /**
     * 入账
     */
    public final static String accTranType_2="2";
    /**
     * 下单异步通知
     */
    public final static String notify_payment="notifyPayment";
    /**
     * 平台支出异步通知
     */
    public final static String notify_payPlatform="notifyPayPlatform";
    /**
     * 支付平台正确返回值
     */
    public final static String pay_success_code="0000";

    /**
     * 购买订单触发几次
     */
    public final static Integer celCount =2;

    /**
     * 等待付款
     */
    public final static Integer payStatus_wait_0=0;
    /**
     * 支付成功
     */
    public final static Integer payStatus_success_1=1;
    /**
     * 支付失败
     */
    public final static Integer payStatus_error_2=2;
    /**
     * 支付关闭
     */
    public final static Integer payStatus_close_3=3;
    /**
     * 部分退款
     */
    public final static Integer payStatus_back_4=4;
    /**
     * 全额退款
     */
    public final static Integer payStatus_allBack_5=5;
    /**
     * 订单类型-》商品
     */
    public final static String orderType_project_01="01";
    /**
     * 订单类型-》套餐
     */
    public final static String orderType_setMeal_02="02";

}

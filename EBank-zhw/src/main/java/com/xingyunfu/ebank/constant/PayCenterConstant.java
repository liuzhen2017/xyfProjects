package com.xingyunfu.ebank.constant;

public interface PayCenterConstant {

    /** 支付状态 payStatus */
    Integer payStatus_0 = 0;        //等待付款
    Integer payStatus_1 = 1;        //支付成功
    Integer payStatus_2 = 2;        //支付失败
    Integer payStatus_3 = 3;        //超时未支付关闭订单
    Integer payStatus_4 = 4;        //部分退款
    Integer payStatus_5 = 5;        //全额退款

    /** remitStatus */
    Integer remitStatus_0 = 0;      //审核中
    Integer remitStatus_1 = 1;      //处理中，已提交通道处理
    Integer remitStatus_2 = 2;      //已成功
    Integer remitStatus_3 = 3;      //已失败

    /** 响应码定义 retCode resCode */
    String retResCode_0 = "0000";       //成功
    String retResCode_1 = "0001";       //未知错误
    String retResCode_2 = "0002";       //签名不正确
    String retResCode_3 = "0100";       //参数有错误
    String retResCode_4 = "0101";       //无效的payCode
    String retResCode_5 = "0102";       //超时限制
    String retResCode_6 = "0200";       //订单信息有误
    String retResCode_7 = "0201";       //订单已过期
    String retResCode_8 = "0202";       //指定的订单不存在
    String retResCode_9 = "0203";       //订单已存在，重复的订单
    String retResCode_10 = "0300";      //风控拒绝
    String retResCode_11 = "0403";      //拒绝访问
    String retResCode_12 = "0500";      //系统错误
    String retResCode_13 = "0510";      //用户商号配置有误
    String retResCode_14 = "0511";      //商户余额不足
    String retResCode_15 = "0520";      //渠道异常

    /** 回调结果 */
    String callback_success = "success";
}

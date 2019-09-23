package com.xinyunfu.customer.constant;

public interface InnerAccountConstant {

    /** 账户类型 accountType */
    String accountType_common = "common";//普通用户
    String accountType_ambassador = "ambassador";//推广大使
    String accountType_supplier = "supplier";//供应链用户
    String accountType_system = "system";//平台账号，此账号只能有一个

    /** 交易类型 transType */
    String transType_out = "out";   //出账
    String transType_entry = "entry";   //入账

    /** 下单异步通知 */
    String notify_payment="notifyPayment";
    /** 平台支出异步通知 */
    String notify_payPlatform="notifyPayPlatform";
    /** 支付平台正确返回值 */
    String pay_success_code="0000";

    /** 用户级别 0普通，1会员*/
    Integer level_0 = 0, level_1 = 1;
}

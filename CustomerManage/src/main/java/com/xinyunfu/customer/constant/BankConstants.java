package com.xinyunfu.customer.constant;

/**
 * 银行支付相关常量
 */
public interface BankConstants {

    /** 银行卡状态 审核通过=1,删除=0 */
    Integer STATUS_DELETE = 0;
    Integer STATUS_APPROVAL = 1;

    /** 账号类型，0个人账号，1企业账号 */
    Integer ACCOUNT_TYPE_PERSON = 0;
    Integer ACCOUNT_TYPE_BUSINESS = 1;
}

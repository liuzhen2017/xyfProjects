package com.xingyunfu.ebank.constant;

/**
 * 商品/套餐常量
 */
public interface ProductRecordConstant {

    /** 产品类型，00商品，01套餐 */
    String productType_goods = "00";
    String productType_combo = "01";

    /** 转账状态， apply_process申请支付中，apply_success申请支付成功，apply_failed申请支付失败, pay_failed支付失败，pay_success支付成功 */
    String status_apply_process = "apply_process";
    String status_apply_success = "apply_success";
    String status_apply_failed = "apply_failed";
    String status_pay_failed = "pay_failed";
    String status_pay_success = "pay_success";
}

package com.xingyunfu.ebank.constant;

/**
 * 流水常量
 */
public interface FlowConstants {

    /** 流水类型，in转入，out转出 */
    String flowType_in = "in";
    String flowType_out = "out";

    /** flowStatus 流水状态,wait等待支付，success支付成功，failed支付失败*/
    String flowStatus_0 = "wait";
    String flowStatus_1 = "success";
    String flowStatus_2 = "failed";

    /** flowSource 流水来源，商品/套餐购买 product, 用户转账 transfer */
    String flowSource_product = "product";
    String flowSource_transfer = "transfer";
}

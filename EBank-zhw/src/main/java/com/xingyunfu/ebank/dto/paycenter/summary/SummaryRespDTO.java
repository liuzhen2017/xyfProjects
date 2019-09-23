package com.xingyunfu.ebank.dto.paycenter.summary;

import lombok.Data;

/**
 * 对账汇总响应题目
 */
@Data
public class SummaryRespDTO {

    private String resCode;
    private String resMsg;
    private Long orderQuantity;     //指定时间段的订单数量（仅包含成功的订单）
    private Long totalAmount;     //指定时间段的交易总金额
}

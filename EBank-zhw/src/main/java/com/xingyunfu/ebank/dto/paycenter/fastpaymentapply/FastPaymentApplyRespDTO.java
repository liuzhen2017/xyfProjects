package com.xingyunfu.ebank.dto.paycenter.fastpaymentapply;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FastPaymentApplyRespDTO {
    private String resCode;
    private String resMsg;
    private String timestamp;
    private String outTradeNo;      //商户订单号
    private Integer amount;         //订单金额
    private String sysOrderNo;      //支付中心订单号
}

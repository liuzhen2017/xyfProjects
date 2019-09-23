package com.xingyunfu.ebank.dto.paycenter.fastpaymentconfirm;

import lombok.Data;

@Data
public class FastPaymentConfirmRespDTO {

    private String resCode;             //业务状态码，注意：不代表订单状态
    private String resMsg;              //错误信息
    private String timestamp;           //时间戳
    private String outTradeNo;          //商户订单号
    private Integer amount;             //订单金额
    private String sysOrderNo;          //支付中心订单号
}

package com.xingyunfu.ebank.dto.paycenter.transfer;

import lombok.Data;

@Data
public class TransferOrderRespDTO {
    private String resCode;         //业务码
    private String resMsg;          //错误信息
    private Long timestamp;         //时间戳
    private String merchantNo;      //商户号
    private String outTradeNo;      //商户订单号
    private Integer amount;         //交易金额，单位：分
    private String voucher;         //交易凭证号
    private Integer fee;            //交易手续费，单位：分
}

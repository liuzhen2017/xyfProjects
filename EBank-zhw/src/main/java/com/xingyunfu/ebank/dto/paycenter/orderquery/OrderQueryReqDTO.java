package com.xingyunfu.ebank.dto.paycenter.orderquery;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderQueryReqDTO {

    private String merchantNo;          //商户号
    private Long timestamp;           //请求时间戳
    private String outTradeNo;          //商户订单号
    private String sysOrderNo;          //支付系统订单号

    public OrderQueryReqDTO(String merchantNo, Long timestamp, String outTradeNo, String sysOrderNo){
        this.merchantNo = merchantNo;
        this.timestamp = timestamp;
        this.outTradeNo = outTradeNo;
        this.sysOrderNo = sysOrderNo;
    }
}

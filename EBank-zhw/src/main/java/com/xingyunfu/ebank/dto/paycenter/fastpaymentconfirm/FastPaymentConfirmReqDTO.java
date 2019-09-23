package com.xingyunfu.ebank.dto.paycenter.fastpaymentconfirm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FastPaymentConfirmReqDTO {
    private String merchantNo;      //商户号
    private Long timestamp;         //请求时间戳
    @JsonProperty("out_trade_no")
    private String outTradeNo;      //商户订单号
    @JsonProperty("mobile_no")
    private String mobileNo;        //银行卡预留手机号
    @JsonProperty("auth_code")
    private String authCode;        //授权码
}

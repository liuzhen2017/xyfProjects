package com.xingyunfu.ebank.dto.paycenter.detail;

import lombok.Data;

@Data
public class DetailOrderListDTO {
    private String subject;
    private String outTradeNo;
    private Double amount;
    private String orderTime;
    private String voucher;
    private Integer status;
    private String submitTime;
    private String dealTime;
    private String billingAccount;
}

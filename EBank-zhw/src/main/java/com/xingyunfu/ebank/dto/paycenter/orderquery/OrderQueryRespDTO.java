package com.xingyunfu.ebank.dto.paycenter.orderquery;

import lombok.Data;

@Data
public class OrderQueryRespDTO {
    private String resCode;
    private String resMsg;
    private Long timestamp;
    private String subject;
    private String outTradeNo;
    private Integer status;
    private Integer amount;
    private Integer fee;
    private String createTime;
    private String dealtime;
    private String sysOrderNo;

}

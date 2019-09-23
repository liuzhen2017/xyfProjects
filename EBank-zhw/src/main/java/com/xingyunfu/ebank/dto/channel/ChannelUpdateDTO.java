package com.xingyunfu.ebank.dto.channel;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChannelUpdateDTO {
    @NotNull
    private Long id;
    private Integer payType;            //支付方式 -- 支付中心
    private Integer tradeType;          //付款方式 -- 支付中心
    private Boolean enable;             //是否有效
}

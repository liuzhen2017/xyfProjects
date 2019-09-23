package com.xinyunfu.dto.ebank;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductRecordAddRespDTO implements Serializable {

    private String orderNo;     //订单号
    private String payNo;       //支付交易号
    private String payLoad;     //支付URL
    private Integer openType;   //请求打开方式

}

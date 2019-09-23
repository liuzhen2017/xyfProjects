package com.xingyunfu.ebank.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRecordAddV2DTO {

    private Long userNo;                    //用户编号
    private String orderNo;                 //购买订单编号，其它服务的订单编号
    private String productType;             //产品类型，00商品，01套餐

    private BigDecimal amount;              //付款金额
    private Integer ebankPayType;             //ebank支付类型
    private String clientType;               //支付渠道 -- ebank客户端类型

    private String returnUrl;               //调用端url，只记录，不处理
    private String serverName;              //发起支付请求的服务
    private Integer orderType;               //订单模块请求参数，原样返回
    private String subject;                 //交易主体
    private String clientIp;                //客户端ip
    private String payTimeOut;              //支付超时时间

    public ProductRecordAddV2DTO(ProductRecordAddDTO var){
        this.userNo = var.getUserNo();
        this.orderNo = var.getOrderNo();
        this.productType = var.getProductType();

        this.amount = var.getAmount();
        this.ebankPayType = var.getPayType();
        this.clientType = var.getChannel().toUpperCase();

        this.returnUrl = var.getReturnUrl();
        this.serverName = var.getServerName();
        this.orderType = var.getOrderType();
        this.subject = var.getSubject();
        this.clientIp = var.getClientIp();
        this.payTimeOut = var.getPayTimeOut();
    }
}

package com.xingyunfu.ebank.dto.product;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 发起购买产品
 */
@Data
public class ProductRecordAddDTO {

    @NotNull
    private Long userNo;                    //用户编号
    @NotEmpty
    private String orderNo;                 //购买订单编号，其它服务的订单编号
    @NotEmpty
    private String productType;             //产品类型，00商品，01套餐

    @NotNull
    private BigDecimal amount;              //付款金额
    @NotNull
    private Integer payType;                //ebank支付类型
    @NotEmpty
    private String channel;                 //支付渠道 -- ebank客户端类型

    private String returnUrl;               //调用端url，只记录，不处理
    @NotEmpty
    private String serverName;              //发起支付请求的服务
    private Integer orderType;               //订单模块请求参数，原样返回
    private String subject;                 //交易主体
    private String clientIp;                //客户端ip

    @NotEmpty
    private String payTimeOut;              //支付超时时间
}

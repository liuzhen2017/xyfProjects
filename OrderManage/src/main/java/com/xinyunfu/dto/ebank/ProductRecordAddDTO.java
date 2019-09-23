package com.xinyunfu.dto.ebank;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 发起购买产品
 */
@Data
public class ProductRecordAddDTO implements Serializable {

    @NotNull
    private Long userNo;                    //用户编号
    @NotEmpty
    private String orderNo;                 //购买订单编号，其它服务的订单编号
    @NotEmpty
    private String productType;             //产品类型，00商品，01套餐
    @NotEmpty
    private BigDecimal amount = new BigDecimal(0);              //付款金额
    @NotEmpty
    private Integer payType;                //支付类型, 2云闪付，16微信支付，32支付宝，64快捷支付，128网银支付
    @NotEmpty
    private String channel;                 //支付渠道
    private String returnUrl;               //调用端url，只记录，不处理
    @NotEmpty
    private String serverName;              //发起支付请求的服务
    private Integer orderType;               //订单模块请求参数，原样返回
    private String subject;                 //交易主体
    private String clientIp;                //客户端ip

    /**
     * 超时时间
     */
    private String payTimeOut;
}

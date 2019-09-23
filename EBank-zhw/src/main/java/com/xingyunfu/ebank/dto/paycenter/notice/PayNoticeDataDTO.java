package com.xingyunfu.ebank.dto.paycenter.notice;

import lombok.Data;

/**
 * 购买商品/套餐
 * 支付通知回调data中数据
 */
@Data
public class PayNoticeDataDTO {
    private String merchantNo;      //商户号
    private Long timestamp;         //时间戳
    private String subject;         //交易主体
    private String outTradeNo;      //商户订单号
    private Integer amount;         //交易金额，单位：分
    private String dealtime;          //成交时间，格式yyyy-MM-dd HH:mm:ss
    private String privateValue;    //商户私有信息，下单时的参数
    private Integer payType;        //支付方式
    private Integer status;         //订单状态
    private Integer settleAmount;   //手续费，单位：分
    private Integer fee;            //手续费，单位：分
    private String version;         //订单版本号
}

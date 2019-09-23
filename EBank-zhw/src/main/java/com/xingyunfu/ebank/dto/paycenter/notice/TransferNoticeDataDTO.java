package com.xingyunfu.ebank.dto.paycenter.notice;

/**
 * 向用户转账
 * 付款结果通知
 */
public class TransferNoticeDataDTO {
    private String merchantNo;      //商户号
    private Long timestamp;         //时间戳
    private String outTradeNo;      //商户订单号
    private Integer amount;      //交易金额，单位：分
    private String createtime;      //订单创建时间，格式yyyy-MM-dd HH:mm:ss
    private String dealtime;        //成交时间，格式yyyy-MM-dd HH:mm:ss
    private Integer status;         //订单状态
    private Integer channelType;    //渠道编号
    private Integer fee;         //手续费，单位：分
    private String voucher;         //订单凭证号
}

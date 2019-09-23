package com.xingyunfu.ebank.dto.paycenter.placeorder;

import lombok.Data;

/**
 * 统一订单支付明文响应对象
 */
@Data
public class PlaceOrderRespDTO {
    private String resCode;     //业务结果代码，0000 表示成功，其他失败
    private String resMsg;      //业务错误信息
    private Long timestamp;     //当前时间戳
    private String outTradeNo;  //商户订单号
    private Integer amount;     //交易金额，单位分
    private String subject;     //交易主题
    private Integer payType;    //支付方式
    private Integer status;     //当前订单状态，见附录 PayStatus
    private String retriveCode; //上游支付单号，例如微信，支付宝等等
    private String sysOrderNo;  //支付中心单号
    private String payload;     //交易数据，例如二维码链接，app 唤醒参数等等，视支付方式而定
}

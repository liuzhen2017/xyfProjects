package com.xingyunfu.ebank.dto.paycenter;

import lombok.Data;

/**
 * 支付中心响应的加密数据
 */
@Data
public class PayCenterRespBaseDTO {
    private String retCode;     //通讯码；0000代表成功，其他失败
    private String retMsg;     //错误消息
    private Long timestamp;     //时间戳，精确到秒
    private String data;        //业务返回密文
    private String sign;        //明文业务参数签名
}

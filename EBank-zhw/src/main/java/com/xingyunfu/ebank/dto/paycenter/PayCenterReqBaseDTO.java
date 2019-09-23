package com.xingyunfu.ebank.dto.paycenter;

import lombok.Data;

/**
 * 向支付中心请求对象，data中数据时加密后的对象
 */
@Data
public class PayCenterReqBaseDTO {
    private String merchantNo;        //商户号
    private String data;              //API请求参数密文
    private String sign;              //明文请求参数签名
}

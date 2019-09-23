package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/8/4
 * @Description :
 */
@Data
public class PayDTO implements Serializable {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 请求的IP
     */
    private String ipAddr;

    /**
     * 支付渠道
     */
    private String channle;
}

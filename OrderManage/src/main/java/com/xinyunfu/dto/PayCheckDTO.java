package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/8/26
 * @Description :
 */
@Data
public class PayCheckDTO implements Serializable {

    /**
     * 唤起支付的订单号
     */
    private String orderNo;

    /**
     * 用户输入的验证码
     */
    private String verifyCode;

}

package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/8/5
 * @Description :
 */
@Data
public class SendCodeDTO implements Serializable {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 发送短信的类型
     */
    private String type = "register";


    /**
     * 图形验证码token
     */
    private String token;

    /**
     *  图形验证码提供厂商
     */
    private Integer tokenType;
}

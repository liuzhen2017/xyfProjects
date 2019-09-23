package com.xinyunfu.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UserRegisterDTO implements Serializable {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 密码
     */
    private String password;

    /**
     * 邀请码
     */
    private String userCode;

    /**
     * 滑动块token
     */
    private String token;
}

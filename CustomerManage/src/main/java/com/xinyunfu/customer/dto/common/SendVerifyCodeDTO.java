package com.xinyunfu.customer.dto.common;

import com.xinyunfu.customer.constant.JSR303Constant;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SendVerifyCodeDTO {
    @NotEmpty@Pattern(regexp = "^[1][\\d]{10}$", message = JSR303Constant.phone)
    private String phone;       //手机号
    @NotEmpty
    private String type;        //短信用途
    private String token;       //图形验证码token
    private Integer tokenType;   //图形验证码提供厂商
}

package com.xinyunfu.customer.dto.user;

import com.xinyunfu.customer.constant.JSR303Constant;
import com.xinyunfu.customer.utils.FuzzyUtil;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserLoginDTO {

    @NotNull@Pattern(regexp = "^1[\\d]{10}$", message = JSR303Constant.phone)
    private String phone;
    private String password;
    @Pattern(regexp = "^[\\d]{6}$", message = JSR303Constant.verifyCode)
    private String verifyCode;
    @NotEmpty
    private String token;
    private Integer tokenType;

    @Override
    public String toString() {
        return "UserLoginDTO{" +
                "phone='" + phone + '\'' +
                ", password='" + FuzzyUtil.fuzzy(password) + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", token='" + token + '\'' +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }

}

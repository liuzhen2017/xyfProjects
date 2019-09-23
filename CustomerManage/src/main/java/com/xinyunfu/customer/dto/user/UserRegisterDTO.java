package com.xinyunfu.customer.dto.user;

import com.xinyunfu.customer.constant.JSR303Constant;
import com.xinyunfu.customer.utils.FuzzyUtil;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterDTO {
    @NotEmpty@Pattern(regexp = "^[1][\\d]{10}$", message = JSR303Constant.phone)
    private String phone;
    @NotEmpty@Pattern(regexp = "^[\\d]{6}$", message = JSR303Constant.verifyCode)
    private String verifyCode;
    @NotEmpty@Length(min = 6, message = JSR303Constant.password)
    private String password;
    @NotEmpty@Pattern(regexp = "^[\\w]{6}$", message = JSR303Constant.userCode)
    private String userCode;
    @NotEmpty
    private String token;

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "phone='" + phone + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", password='" + FuzzyUtil.fuzzy(password) + '\'' +
                ", userCode='" + userCode + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

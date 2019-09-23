package com.xinyunfu.customer.dto.user;

import com.xinyunfu.customer.constant.JSR303Constant;
import com.xinyunfu.customer.utils.FuzzyUtil;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 根据验证码，设置密码
 */
@Data
public class UserResetVerifyDTO {

    @NotEmpty@Pattern(regexp = "^[1][\\d]{10}$", message = JSR303Constant.phone)
    private String phone;
    @NotEmpty@Pattern(regexp = "^[\\d]{6}$", message = JSR303Constant.verifyCode)
    private String verifyCode;
    @NotEmpty@Length(min = 6, message = JSR303Constant.password)
    private String newPassword;
    @NotEmpty@Length(min = 6, message = JSR303Constant.password)
    private String confirmPassword;
    private String token;

    @Override
    public String toString() {
        return "UserResetVerifyDTO{" +
                "phone='" + phone + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", newPassword='" + FuzzyUtil.fuzzy(newPassword) + '\'' +
                ", confirmPassword='" + FuzzyUtil.fuzzy(confirmPassword) + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

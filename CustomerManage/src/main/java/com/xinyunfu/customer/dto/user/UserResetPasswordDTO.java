package com.xinyunfu.customer.dto.user;

import com.xinyunfu.customer.constant.JSR303Constant;
import com.xinyunfu.customer.utils.FuzzyUtil;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 根据旧密码，设置新的支付密码
 */
@Data
public class UserResetPasswordDTO {

    private String oldPassword;      //旧密码
    @NotEmpty@Length(min = 6, message = JSR303Constant.password)
    private String newPassword;      //新密码
    @NotEmpty@Length(min = 6, message = JSR303Constant.password)
    private String confirmPassword;  //确认新密码

    @Override
    public String toString() {
        return "UserResetPasswordDTO{" +
                "oldPassword='" + FuzzyUtil.fuzzy(oldPassword) + '\'' +
                ", newPassword='" + FuzzyUtil.fuzzy(newPassword) + '\'' +
                ", confirmPassword='" + FuzzyUtil.fuzzy(confirmPassword) + '\'' +
                '}';
    }
}

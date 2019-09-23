package com.xinyunfu.customer.dto.user;

import com.xinyunfu.customer.utils.FuzzyUtil;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 身份证校验数据
 */
@Data
public class UserCardCheckDTO {

    @NotEmpty
    private String userName;
    @NotEmpty
    private String cardNo;
    @NotEmpty
    private String endTime;

    @Override
    public String toString() {
        return "UserCardCheckDTO{" +
                "userName='" + userName + '\'' +
                ", cardNo='" + FuzzyUtil.fuzzy(cardNo) + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}

package com.xinyunfu.customer.dto.common;

import com.xinyunfu.customer.utils.FuzzyUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 推送时用到的参数
 */
@NoArgsConstructor
@Data
public class PushMessageDTO {
    private String token;
    private String clientId;
    private String phoneType;

    @Override
    public String toString() {
        return "PushMessageDTO{" +
                "token='" + FuzzyUtil.fuzzy(token) + '\'' +
                ", clientId='" + FuzzyUtil.fuzzy(clientId) + '\'' +
                ", phoneType='" + phoneType + "\'" +
                '}';
    }
}

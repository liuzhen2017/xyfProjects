package com.xinyunfu.customer.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSendCodeDTO {
    private Long duration;   //验证码存活时间
}

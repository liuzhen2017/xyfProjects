package com.xinyunfu.customer.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserPayPasswordCheckDTO {

    @NotEmpty
    private String payPassword;
}

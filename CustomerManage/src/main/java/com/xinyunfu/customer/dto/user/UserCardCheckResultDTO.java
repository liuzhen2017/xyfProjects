package com.xinyunfu.customer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCardCheckResultDTO {

    private Boolean result;
    private String message;
}

package com.xinyunfu.customer.dto.bank;

import com.xinyunfu.customer.dto.user.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountExtensionAddSuperDTO {

    private UserInfoDTO superUserInfo;
    private List<UserInfoDTO> userInfoList;
}

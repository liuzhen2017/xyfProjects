package com.xingyunfu.ebank.dto.extension;

import com.xingyunfu.ebank.dto.user.UserInfoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class AccountExtensionAddSuperDTO {

    @NotNull
    private UserInfoDTO superUserInfo;
    @NotEmpty
    private List<UserInfoDTO> userInfoList;
}

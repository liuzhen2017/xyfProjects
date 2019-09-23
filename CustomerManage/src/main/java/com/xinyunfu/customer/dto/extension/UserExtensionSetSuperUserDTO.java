package com.xinyunfu.customer.dto.extension;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 设置上级用户
 */
@Data
@NoArgsConstructor
public class UserExtensionSetSuperUserDTO {

    @NotEmpty
    private String superPhone;
    @NotEmpty
    private List<String> phoneList;
}

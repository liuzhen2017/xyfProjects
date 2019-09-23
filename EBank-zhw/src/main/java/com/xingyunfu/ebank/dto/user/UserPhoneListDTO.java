package com.xingyunfu.ebank.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class UserPhoneListDTO {
    @NotEmpty
    private List<String> phone;
}

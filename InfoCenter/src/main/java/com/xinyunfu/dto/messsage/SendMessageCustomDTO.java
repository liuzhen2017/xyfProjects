package com.xinyunfu.dto.messsage;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class SendMessageCustomDTO {
    @NotEmpty
    private String templateName;
    @NotEmpty
    private List<String> phone;
    private List<Object> data;
}

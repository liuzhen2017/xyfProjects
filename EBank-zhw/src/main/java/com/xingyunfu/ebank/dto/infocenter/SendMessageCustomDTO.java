package com.xingyunfu.ebank.dto.infocenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageCustomDTO {
    private String templateName;
    private List<String> phone;
    private List<Object> data;
}

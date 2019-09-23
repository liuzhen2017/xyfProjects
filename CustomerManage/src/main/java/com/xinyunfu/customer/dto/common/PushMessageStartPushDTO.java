package com.xinyunfu.customer.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushMessageStartPushDTO implements Serializable {
    private Long userNo;
    @NotNull
    private String templateName;        //推送模板001/002
    private List<Object> data;
}

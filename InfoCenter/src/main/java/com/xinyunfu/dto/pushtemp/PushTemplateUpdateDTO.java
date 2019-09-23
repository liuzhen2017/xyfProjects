package com.xinyunfu.dto.pushtemp;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PushTemplateUpdateDTO {
    @NotNull
    private Long id;
    private String title;               //推送标题
    private String content;             //推送内容模板
    private Boolean enable;             //是否可用
    private String remark;              //模板说明
}

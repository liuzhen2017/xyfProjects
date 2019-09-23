package com.xinyunfu.dto.pushtemp;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PushTemplateAddDTO {
    @NotEmpty
    private String templateName;        //模板名称唯一标识
    @NotEmpty
    private String templateType;        //推送类型，短信shortMessage/极光pushMessage/微信wechatMessage
    @NotEmpty
    private String title;               //推送标题
    @NotEmpty
    private String content;             //推送内容模板
    @NotNull
    private Boolean enable;             //是否可用
    private String remark;              //模板说明
}

package com.ruoyi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageCustomDTO {
    private String templateName;        //	推送模板
    private List<String> phone;     //手机号列表
    private List<Object> data;      //模板中插入的数据
}

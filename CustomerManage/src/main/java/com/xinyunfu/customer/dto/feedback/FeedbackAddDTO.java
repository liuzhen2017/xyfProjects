package com.xinyunfu.customer.dto.feedback;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 添加反馈
 */
@Data
public class FeedbackAddDTO {

    @NotEmpty
    private String content;         //反馈内容
    @NotEmpty
    private String contentType;     //反馈类型
    private String contact;         //联系方式
    private String contactType;     //联系方式类型
    private List<String> images;    //图片
}

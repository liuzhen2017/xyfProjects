package com.xinyunfu.customer.dto.imagetext;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 上传base64图片
 */
@Data
public class PictureObjectDTO {
    @NotEmpty
    private String image;
    @NotEmpty
    private String type;
}

package com.xinyunfu.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author TYM
 * @date 2019/8/14
 * @Description :分类跳转图片对象
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChannelImgDTO {

    private String imgUrl;             //图片路径
    private String forwardType;       //跳转类型  1链接 2分类 3商品
    private String linkUrl;            //跳转类型为1,链接 2分类id 3商品id
}

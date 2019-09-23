package com.xinyunfu.product.dto;

import lombok.Data;

import java.util.List;

/**
 * @author TYM
 * @date 2019/9/4
 * @Description :
 */
@Data
public class HomePageChannelDto {
    /**
     * 分类id
     */
    private Long channelId;
    /**
     * 分类名称
     */
    private String channelName;
    /**
     * 栏目类型
     */
    private Long channelTypeId;
    /**
     * 父级id
     */
    private Long fatherId;
    /**
     * 展示图片
     */
    private String imageUrl;
    /**
     * 展示图片背景颜色(为轮播图片时需要,其他位置为空)
     */
    private String color;
    /**
     * 跳转类型
     */
    private Integer forwardType;
    /**
     * 跳转路径
     */
    private String linkUrl;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 子栏目
     */
    private List<HomePageChannelDto> childs;
    /**
     * 栏目下的商品
     */
    private List<ProDto> proDtos;
}

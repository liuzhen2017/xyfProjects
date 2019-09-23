package com.xinyunfu.product.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TYM
 * @date 2019/8/12
 * @Description :
 */
@Data
public class BannerDTO implements Serializable
{

    /** 广告id */
    private Long bannerId;
    /** 广告名称 */
    private String bannerName;
    /** 图片路径 */
    private String proName;
    private Long proId;
    private String channelName;
    private Long channelId;
    private String imgUrl;
    /** 广告链接 */
    private String linkUrl;
    /** 开始时间 */
    private String startDate;
    /** 结束时间 */
    private String endDate;
    /** 排序号 */
    private Integer sortNumber;
    /** 状态 */
    private Integer status;


    private String color;
    private String forwardType;





}

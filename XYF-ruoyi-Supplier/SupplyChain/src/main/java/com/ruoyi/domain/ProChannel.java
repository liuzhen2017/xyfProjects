package com.ruoyi.domain;

import lombok.Data;

/**
 * 类目表 pro_channel
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Data
public class ProChannel {

    /**
     * ID
     */
    private String id;
    /**
     * 栏目ID
     */
    private String channelId;
    /**
     * 栏目名称
     */
    private String channelName;
    /**
     * 上级栏目ID
     */
    private String fatherId;
    /**
     * 上级栏目名称
     */
    private String fatherName;
    /**
     * 排序号
     */
    private Integer sortNumber;
    /**
     * 栏目展示的图片 （非必须）
     */
    private String imageUrl;
    /**
     * 栏目跳转的路径 （非必须）
     */
    private String linkUrl;
    /**
     * 状态 0 可用  / 1禁用
     */
    private Integer status;
    /**
     * 栏目备注 （非必须）
     */
    private String remarks;
    private String createdBy;
    private String createdTime;
    private String updatedBy;
    private String updatedTime;


    /**
     *  栏目类型ID
     */
    private String channelTypeId;
    /**
     *  栏目类型
     */
    private String channelTypeName;

}

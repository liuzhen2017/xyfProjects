package com.ruoyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

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
     * 跳转类型  0=无操作 1=链接 2=活动分类 3=商品分类 4=商品详情
     */
    private String forwardType;
    /**
     * 展示图片背景颜色（非必须）
     */
    private String color;
    /**
     * 栏目跳转的路径 （非必须）
     */
    private String linkUrl;
    /**
     * 状态 0 可用  / 1禁用
     */
    private String status;
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

    /**
     * 当前子级分类的数量
     */
    private String amount;

    /**
     * 是否为当前分类下搜索  true
     */
    private boolean selectAll;

}

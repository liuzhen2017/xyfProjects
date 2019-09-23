package com.xinyunfu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author XRZ
 * @date 2019/8/8
 * @Description : 消息表
 */
@Data
public class InfoCenterMessage implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 用户编号
     */
    private Long userNo;

    /**
     * 模板编号
     */
    private String templateName;

    /**
     * 推送类型，短信shortmessage/极光pushmessage/微信wechatmessage
     */
    private String templateType;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String context;

    /**
     * 创建人
     */
    @JsonIgnore
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Timestamp createdDate;

    /**
     * 最后修改人
     */
    @JsonIgnore
    private String updatedBy;

    /**
     * 最后修改时间
     */
    @JsonIgnore
    private Timestamp updatedDate;

    /**
     * 是否可用（可用=1，禁用=0）
     */
    @JsonIgnore
    private Integer enable;

    /**
     * 消息发送时间
     */
    @TableField(exist = false)
    private String dateTime;

}

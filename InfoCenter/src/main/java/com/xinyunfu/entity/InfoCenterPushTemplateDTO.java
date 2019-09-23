package com.xinyunfu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinyunfu.dto.pushtemp.PushTemplateAddDTO;
import com.xinyunfu.dto.pushtemp.PushTemplateUpdateDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@TableName("infocenter_push_template")
public class InfoCenterPushTemplateDTO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String templateName;        //模板名称唯一标识
    private String templateType;        //推送类型，短信shortMessage/极光pushMessage/微信wechatMessage

    private String title;               //推送标题
    private String content;             //推送内容模板
    private Boolean enable;             //是否可用

    private String remark;              //模板说明
    private Timestamp createTime;           //创建使劲按
    private Timestamp lastModifyTime;       //最后修改时间

    public InfoCenterPushTemplateDTO(PushTemplateAddDTO templateAdd){
        this.templateName = templateAdd.getTemplateName();
        this.templateType = templateAdd.getTemplateType();
        this.title = templateAdd.getTitle();
        this.content = templateAdd.getContent();
        this.enable = templateAdd.getEnable();
        this.remark = templateAdd.getRemark();
    }

    public InfoCenterPushTemplateDTO(PushTemplateUpdateDTO pushTemplateUpdate){
        this.id = pushTemplateUpdate.getId();
        this.title = pushTemplateUpdate.getTitle();
        this.content = pushTemplateUpdate.getContent();
        this.enable = pushTemplateUpdate.getEnable();
        this.remark = pushTemplateUpdate.getRemark();
    }
}

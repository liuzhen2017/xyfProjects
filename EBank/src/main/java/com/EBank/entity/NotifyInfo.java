package com.EBank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuzhen
 * accFlow 实体
 */
@Data
@NoArgsConstructor
public class NotifyInfo {
    @TableId(value="ID", type= IdType.AUTO)
    private Integer id;
    private String notifyObject;
    private String notifyParmat;
    private String notifyStatus;
    private Integer replyCount;
    private String errMsg;
    private String notifyClass;
    private String notifyMethod;
}

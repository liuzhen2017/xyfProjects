package com.xinyunfu.jace.entites.entite;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuzhen
 * accFlow 实体
 */
@Data
@NoArgsConstructor
public class NotifyInfo {
    private Integer id;
    private String notifyObject;
    private String notifyParmat;
    private String notifyStatus;
    private Integer replyCount;
    private String errMsg;
    private String notifyClass;
    private String notifyMethod;
}

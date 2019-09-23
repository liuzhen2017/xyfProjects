package com.xinyunfu.dto.pushmessage;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * mq中需要消费的推送对象
 */
@Data
public class PushMessageStartPushDTO implements Serializable {
    private Long userNo;
    private String templateName;        //推送模板001/002
    private List<Object> data;
}

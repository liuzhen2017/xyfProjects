package com.xingyunfu.ebank.domain.log;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EbankLogDTO {
    private Long id;
    private String logKey;              //日志内容对应的key，与日志类型相关
    private String content;             //日志类型
    private Timestamp createTime;       //日志内容
}

package com.xingyunfu.ebank.dto.paycenter.summary;

import lombok.Data;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 对账汇总请求体
 */
@Data
public class SummaryReqDTO {

    private String merchantNo;
    private Long timestamp = Instant.now().getEpochSecond();
    private String startTime;
    private String endTime;

    public SummaryReqDTO(String merchantNo, String startTimeStr, String endTimeStr){
        this.merchantNo = merchantNo;
        this.startTime = startTimeStr;
        this.endTime = endTimeStr;
    }
}

package com.xingyunfu.ebank.dto.paycenter.detail;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class DetailReqDTO {
    private String merchantNo;
    private Long timestamp = Instant.now().getEpochSecond();
    private String startTime;
    private String endTime;

    public DetailReqDTO(String merchantNo, String startTimeStr, String endTimeStr){
        this.merchantNo = merchantNo;
        this.startTime = startTimeStr;
        this.endTime = endTimeStr;
    }
}

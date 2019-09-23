package com.xinyunfu.customer.domain.log;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.util.Times;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class CustLogDTO {

    private Long id;
    private Long userNo;
    private String logType;
    private String userIp;
    private String details;
    private Timestamp createTime;
    private Timestamp lastModifyTime;

    public CustLogDTO(Long userNo, String logType, String details) {
        this.userNo = userNo;
        this.logType = logType;
        this.details = details;
    }

    public CustLogDTO(Long userNo, String logType) {
        this.userNo = userNo;
        this.logType = logType;
    }
}

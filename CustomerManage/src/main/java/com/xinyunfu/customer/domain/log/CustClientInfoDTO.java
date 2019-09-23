package com.xinyunfu.customer.domain.log;

import com.xinyunfu.customer.dto.log.ClientInfoAddDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class CustClientInfoDTO {
    private Long id;
    private Long userNo;
    private String clientVersion;
    private String clientSystem;
    private Integer source;
    private String serverVersion;
    private Timestamp createTime;

    public CustClientInfoDTO(ClientInfoAddDTO clientInfoAdd, Long userNo){
        this.userNo = userNo;
        this.clientVersion = clientInfoAdd.getClientVersion();
        this.clientSystem = clientInfoAdd.getClientSystem();
        this.source = clientInfoAdd.getSource();
        this.serverVersion = "1.0";
    }
}

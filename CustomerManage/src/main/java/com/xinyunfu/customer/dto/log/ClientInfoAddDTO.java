package com.xinyunfu.customer.dto.log;

import lombok.Data;

@Data
public class ClientInfoAddDTO {
    private String clientVersion;
    private String clientSystem;
    private Integer source;
}

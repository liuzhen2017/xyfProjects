package com.xinyunfu.dto.pushmessage;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * 保存可推送的设备信息
 */
@Data
public class PushMessageAddDeviceDTO {
    @NotNull
    private Long userNo;
    private String phone;
    private String token;
    private String clientId;
    private String phoneType;
}

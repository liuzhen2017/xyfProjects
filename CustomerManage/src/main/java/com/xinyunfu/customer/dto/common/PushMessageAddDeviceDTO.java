package com.xinyunfu.customer.dto.common;

import lombok.Data;

@Data
public class PushMessageAddDeviceDTO {
    private Long userNo;
    private String phone;
    private String token;
    private String clientId;
    private String phoneType;

    public PushMessageAddDeviceDTO(PushMessageDTO pushMessage){
        this.token = pushMessage.getToken();
        this.clientId = pushMessage.getClientId();
        this.phoneType = pushMessage.getPhoneType();
    }
}

package com.xinyunfu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinyunfu.dto.pushmessage.PushMessageAddDeviceDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@TableName("infocenter_push_device")
public class InfoCenterPushDeviceDTO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userNo;
    private String phone;
    private String token;
    private String clientId;
    private String phoneType;
    private Timestamp createTime;
    private Timestamp lastModifyTime;

    public InfoCenterPushDeviceDTO(PushMessageAddDeviceDTO addDevice){
        this.userNo = addDevice.getUserNo();
        this.phone = addDevice.getPhone();
        this.token = addDevice.getToken();
        this.clientId = addDevice.getClientId();
        this.phoneType = addDevice.getPhoneType();
    }
}

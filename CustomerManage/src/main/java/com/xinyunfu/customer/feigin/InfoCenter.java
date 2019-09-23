package com.xinyunfu.customer.feigin;

import com.rnmg.jace.utils.ResponseInfo;
import com.xinyunfu.customer.dto.common.PushMessageAddDeviceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("InfoCenter")
@RequestMapping
public interface InfoCenter {

    /**
     * 校验验证码
     */
    @PostMapping("/send/checkSendMsg.do")
    ResponseInfo<Boolean> checkSendMsg(@RequestBody String req);

    /**
     * 发送验证码
     */
    @PostMapping("/send/send/message")
    ResponseInfo<String> sendMsa(@RequestBody String req);

    /**
     * 设置设备端信息，用于推送
     */
    @PostMapping("/infoCenter/push/device")
    ResponseInfo<Void> setDeviceMessage(@RequestBody PushMessageAddDeviceDTO addDevice);
}

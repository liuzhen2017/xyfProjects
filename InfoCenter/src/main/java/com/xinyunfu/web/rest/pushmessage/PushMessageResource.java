package com.xinyunfu.web.rest.pushmessage;

import com.xinyunfu.dto.pushmessage.PushMessageAddDeviceDTO;
import com.xinyunfu.service.InfoCenterMessageService;
import com.xinyunfu.service.pushmessage.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * 推送相关接口
 */
@Slf4j
@RestController
@RequestMapping("/infoCenter/push")
public class PushMessageResource {

    @Autowired private PushMessageService pushMessageService;

    @Autowired
    private InfoCenterMessageService infoCenterMessageService;

    /**
     * 绑定用户关系
     * @param addDevice
     */
    @PostMapping("/device")
    public void setDeviceMessage(@RequestBody PushMessageAddDeviceDTO addDevice){
        log.info("REST set device message. addDevice: {}", addDevice);
        pushMessageService.setDeviceMessage(addDevice);
        log.info("REST set device message. success!");
    }

    /**
     *  接受消息
     * @param message
     * @throws UnsupportedEncodingException
     */
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "info_fenter_push_message_queue",durable = "true"),
            exchange = @Exchange(name = "info_center_exchange",type = ExchangeTypes.DIRECT),
            key = "info_center_key"))
    public void startPush(String message) {
        log.info("Start push, message: {}", message);
        try {
            // 持久化
            infoCenterMessageService.insert(message);
            pushMessageService.startPush(message);
        }catch (Exception e){
            log.error("start push error: {}", e);
        }
    }
}

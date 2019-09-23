package com.xinyunfu.web.rest.message;

import com.xinyunfu.dto.messsage.SendMessageCustomDTO;
import com.xinyunfu.service.message.SendMessageMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 短信发送
 */
@Slf4j
@RestController
@RequestMapping("/infoCenter/message")
public class SendMessageResource {
    @Autowired private SendMessageMangerService sendMessageMangerService;

    /**
     * 自定义内容短信发送
     */
    @PostMapping("/custom")
    public void sendMessage(@RequestBody@Validated SendMessageCustomDTO custom){
        log.info("REST request to send message custom. custom: {}", custom);
        if(Objects.isNull(custom.getData())) custom.setData(new ArrayList<>());
        sendMessageMangerService.sendMessageCustom(custom);
        log.info("REST request to send message custom. success!");
    }
}

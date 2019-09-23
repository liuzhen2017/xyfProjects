package com.xinyunfu.service.message;

import com.xinyunfu.config.PushMessageConfig;
import com.xinyunfu.dto.messsage.SendMessageCustomDTO;
import com.xinyunfu.entity.InfoCenterPushTemplateDTO;
import com.xinyunfu.service.pushtemp.PushTemplateMangerService;
import com.xinyunfu.util.EncryptionTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Slf4j
@Service
public class SendMessageMangerService {
    @Autowired private PushTemplateMangerService pushTemplateMangerService;
    @Autowired private PushMessageConfig pushMessageConfig;
    @Autowired private RestTemplate restTemplate;

    public void sendMessageCustom(SendMessageCustomDTO custom){

        InfoCenterPushTemplateDTO template = pushTemplateMangerService.findByTemplateName(custom.getTemplateName());
        String content = String.format(template.getContent(), custom.getData().toArray());

        custom.getPhone().forEach(var -> this.sendMessageCustom(var, content));
    }

    public void sendMessageCustom(String phone, String conent){
        String dataTemp = "{\"MerchantNo\":\"%s\", \"ClientVersion\":\"%s\", \"ClientSystem\":\"%s\"," +
                "\"ClientSource\":%s, \"TimeStamp\":%s, \"Device_Id\": \"%s\", \"Token\": null, \"InterfaceVersion\":1, " +
                "\"Mobileno\": \"%s\", \"Content\": \"%s\"}";

        String data = String.format(dataTemp, pushMessageConfig.getMerchantNo(), "0.0.0.0", "UNIT-TEST",
                3, Instant.now().getEpochSecond(), "api_test_v2", phone, conent);
        String sign = EncryptionTools.md5(data, pushMessageConfig.getMerchantNoKey());
        log.debug("data: {}", data);
        log.debug("sign: {}", sign);

        String result = restTemplate.postForObject(pushMessageConfig.getMessageCustom(), null, String.class,
                pushMessageConfig.getMerchantNo(), data, sign);
        log.info("result: {}", result);
    }
}

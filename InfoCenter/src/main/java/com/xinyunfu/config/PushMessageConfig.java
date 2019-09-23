package com.xinyunfu.config;

import com.xinyunfu.entity.InfoCenterPushDeviceDTO;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.xinyunfu.Constant.PushMessageConstant.*;

@Data
@Component
@ConfigurationProperties(prefix = "info-center.push")
public class PushMessageConfig {
    /**
     * 推送相关内容
     */
    private String shortMessage;
    private String pushMessage;
    private String wechatMessage;
    private String conentName;

    /**
     * 商户号/密钥
     */
    private String merchantNo;
    private String merchantNoKey;
    private String pushUrl;
    private String messageCustom;

    private static Map<String, String> pushTemplate = null;

    @PostConstruct
    private void init(){
        pushTemplate = new HashMap<String, String>(){{
            put(pushType_0, shortMessage);
            put(pushType_1, pushMessage);
            put(pushType_2, wechatMessage);
        }};
    }

    public String getTemplateNo(String tempType){
        return pushTemplate.get(tempType);
    }

    public String getReceiver(String tempKey, InfoCenterPushDeviceDTO pushDevice){
        String receiver = null;
        switch (tempKey){
            case pushType_0: receiver = pushDevice.getPhone(); break;
            case pushType_1: receiver = pushDevice.getClientId(); break;
        }
        return receiver;
    }
}

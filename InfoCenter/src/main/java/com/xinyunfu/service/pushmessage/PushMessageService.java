package com.xinyunfu.service.pushmessage;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.Constant.PushMessageConstant;
import com.xinyunfu.config.PushMessageConfig;
import com.xinyunfu.dto.pushmessage.PushMessageAddDeviceDTO;
import com.xinyunfu.dto.pushmessage.PushMessageStartPushDTO;
import com.xinyunfu.entity.InfoCenterPushDeviceDTO;
import com.xinyunfu.entity.InfoCenterPushTemplateDTO;
import com.xinyunfu.mapper.InfoCenterPushDeviceMapper;
import com.xinyunfu.service.pushtemp.PushTemplateMangerService;
import com.xinyunfu.service.redis.RedisCommonService;
import com.xinyunfu.service.redis.RedisKeyRules;
import com.xinyunfu.service.redis.RedisStringService;
import com.xinyunfu.util.EncryptionTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class PushMessageService {

    @Autowired private InfoCenterPushDeviceMapper infoCenterPushDeviceMapper;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private RedisStringService redisStringService;
    @Autowired private PushTemplateMangerService pushTemplateMangerService;
    @Autowired private PushMessageConfig pushMessageConfig;
    @Autowired private RestTemplate restTemplate;

    public void setDeviceMessage(PushMessageAddDeviceDTO addDevice){
        InfoCenterPushDeviceDTO pushDevice = this.findByUserNo(addDevice.getUserNo());
        if(Objects.isNull(pushDevice)){
            pushDevice = new InfoCenterPushDeviceDTO(addDevice);
        }else{
            pushDevice.setToken(addDevice.getToken());
            pushDevice.setClientId(addDevice.getClientId());
            pushDevice.setPhoneType(addDevice.getPhoneType());
        }
        this.addOrUpdate(pushDevice);
    }


    public InfoCenterPushDeviceDTO findByUserNo(Long userNo){
        String redisKey = RedisKeyRules.pushDeviceMessage(userNo);
        if(redisCommonService.exists(redisKey)){
            return (InfoCenterPushDeviceDTO) redisStringService.get(redisKey);
        }
        InfoCenterPushDeviceDTO pushDevice = infoCenterPushDeviceMapper.findByUserNo(userNo);
        redisStringService.add(redisKey, pushDevice);
        return pushDevice;
    }

    public void addOrUpdate(InfoCenterPushDeviceDTO pushDevice){
        infoCenterPushDeviceMapper.addOrUpdate(pushDevice);
        redisCommonService.delete(RedisKeyRules.pushDeviceMessage(pushDevice.getUserNo()));
    }

    public void startPush(String message){
        String dataTemp = "{\"MerchantNo\":\"%s\",\"ClientVersion\":\"%s\",\"ClientSystem\":\"%s\"," +
                            "\"ClientSource\":%s,\"TimeStamp\":\"%s\",\"Device_Id\":\"%s\"," +
                            "\"InterfaceVersion\":\"1\",\"Title\":\"%s\", " +
                            "\"Body\":[{" +
                                "\"TemplateNo\":\"%s\", " +
                                "\"Receiver\":\"%s\","+
                                "\"Parameter\":{" +
                                    "\"content\":\"%s\"" +
                            "}}]}";
        PushMessageStartPushDTO startPush = JSONObject.parseObject(message, PushMessageStartPushDTO.class);
        if(Objects.isNull(startPush.getData())) startPush.setData(new ArrayList<>());

        //推送信息/体送模板
        InfoCenterPushDeviceDTO pushDevice = this.findByUserNo(startPush.getUserNo());
        InfoCenterPushTemplateDTO pushTemplate = pushTemplateMangerService.findByTemplateName(startPush.getTemplateName());
        String templateNo = pushMessageConfig.getTemplateNo(pushTemplate.getTemplateType());
        //推送签名
        String data = String.format(dataTemp, pushMessageConfig.getMerchantNo(), "1.0", pushDevice.getPhoneType(),
                PushMessageConstant.phoneTypeMap.get(pushDevice.getPhoneType()), new Date().getTime()/1000, pushDevice.getClientId(),
                pushTemplate.getTitle(), templateNo, pushMessageConfig.getReceiver(pushTemplate.getTemplateType(), pushDevice),
                String.format(pushTemplate.getContent(), startPush.getData().toArray()));
        String sign = EncryptionTools.md5(data, pushMessageConfig.getMerchantNoKey());
        log.debug("data: {}", data);
        log.debug("sign: {}", sign);
        log.debug("key: {}", pushMessageConfig.getMerchantNoKey());

        //推送地址 http://api.msg.yx18g.cn/send/index?MerchantNo={MerchantNo}&Data={Data}&Sign={Sign}
        String res = restTemplate.postForObject(pushMessageConfig.getPushUrl(), null, String.class,
                pushMessageConfig.getMerchantNo(), data, sign);
        log.debug("result: {}", res);
    }
}

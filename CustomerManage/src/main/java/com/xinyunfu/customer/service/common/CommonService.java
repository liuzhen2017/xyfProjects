package com.xinyunfu.customer.service.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinyunfu.customer.constant.CommonConstants;
import com.xinyunfu.customer.domain.user.CustUserDTO;
import com.xinyunfu.customer.dto.common.*;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.feigin.InfoCenter;
import com.xinyunfu.customer.repository.user.CustUserMapper;
import com.xinyunfu.customer.service.rabbitmq.VerifyCode;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisKeyRules;
import com.xinyunfu.customer.service.redis.RedisStringService;
import com.xinyunfu.customer.service.user.UserManagerService;
import com.xinyunfu.customer.utils.JsonMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

import static com.xinyunfu.customer.exception.CustomerExceptionEnum.USER_REGISTER_NON_PHONE;
import static com.xinyunfu.customer.exception.CustomerExceptionEnum.USER_REGISTER_PHONE;

@Slf4j
@Service
public class CommonService {
    @Autowired private VerifyCode verifyCode;
    @Autowired private InfoCenter infoCenter;
    @Autowired private UserManagerService userManagerService;
    @Autowired private CustUserMapper custUserMapper;
    @Autowired private RabbitTemplate rabbitTemplate;
    @Autowired private RedisStringService redisStringService;
    @Autowired private RedisCommonService redisCommonService;

    /**
     * 发送短信验证码
     */
    public Long sendVerifyCode(String phone, String type) throws CustomerException {
        log.info("Send verify code for {}. phone : {}",type, phone);

        CustUserDTO user = custUserMapper.findByPhone(phone);
        //注册，用户需要不存在
        if(CommonConstants.REGISTER.equals(type) && Objects.nonNull(user)) throw new CustomerException(USER_REGISTER_PHONE);
        //重置密码（登陆，支付密码），用户需要存在
        if((CommonConstants.RESET_PAY_PASSWORD.equals(type)||CommonConstants.RESET_PASSWORD.equals(type)) && Objects.isNull(user))
            throw new CustomerException(USER_REGISTER_NON_PHONE);

        verifyCode.send(phone, type);
        return 5*60L;
    }

    /**
     * 设置设备推送相关信息
     */
    public void setPushInfo(Long userNo, PushMessageDTO pushMessage) throws JsonProcessingException {
        PushMessageAddDeviceDTO addDevice = new PushMessageAddDeviceDTO(pushMessage);
        CustUserDTO user = userManagerService.findByUserNo(userNo);

        addDevice.setPhone(user.getPhone());
        addDevice.setUserNo(userNo);
        infoCenter.setDeviceMessage(addDevice);

        //如果是第一次登陆，发起推送，欢迎新用户
        if(redisCommonService.exists(RedisKeyRules.userFirstLogin(userNo))){
            this.startPush(new PushMessageStartPushDTO(user.getUserNo(), "005", new ArrayList<>()));
            redisCommonService.delete(RedisKeyRules.userFirstLogin(userNo));
        }
    }

    /**
     * 发起推送
     */
    public void startPush(PushMessageStartPushDTO startPush) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(startPush);
        log.debug("data: {}", data);
        rabbitTemplate.convertAndSend("info_center_exchange","info_center_key", data);
    }
}

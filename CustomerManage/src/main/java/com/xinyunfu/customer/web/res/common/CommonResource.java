package com.xinyunfu.customer.web.res.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xinyunfu.customer.constant.CommonConstants;
import com.xinyunfu.customer.dto.common.*;
import com.xinyunfu.customer.dto.imagetext.PictureObjectDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.service.checktoken.AllCheckTokenService;
import com.xinyunfu.customer.service.common.CommonService;
import com.xinyunfu.customer.service.imagetext.ImageTextService;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisKeyRules;
import com.xinyunfu.customer.service.redis.RedisStringService;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.xinyunfu.customer.constant.CommonConstants.*;
import static com.xinyunfu.customer.exception.CustomerExceptionEnum.COMMON_CHECK_TOKEN_ERROR;

/**
 * 各个模块都用到的接口
 */
@Slf4j
@RestController
@RequestMapping("/customer/common")
public class CommonResource {
    @Autowired private CommonService commonService;
    @Autowired private ImageTextService imageTextService;
    @Autowired private AllCheckTokenService allCheckTokenService;
    @Autowired private RedisStringService redisStringService;
    @Autowired private RedisCommonService redisCommonService;

    /**
     * 根据手机号向用户发送验证码
     */
    @PostMapping("/code")
    public UserSendCodeDTO code(@RequestHeader(header_ip)String ip, @RequestBody @Validated SendVerifyCodeDTO sendCheck) throws Exception {

        log.info("REST request to send code.ip: {}, sendCheck: {}", ip, sendCheck);

        //校验前端图形验证码
        if(!sendCheck.getType().equals(RESET_PAY_PASSWORD)) {//支付密码不需要图形验证码
            if (!allCheckTokenService.checkToken(sendCheck.getToken(), sendCheck.getTokenType(), ip))
                throw new CustomerException(COMMON_CHECK_TOKEN_ERROR);
        }
        //发送短信
        Long offset = commonService.sendVerifyCode(sendCheck.getPhone(), sendCheck.getType());
        UserSendCodeDTO result = new UserSendCodeDTO(offset);

        //发送短信成功，将图形验证码token写入redis，超时时间5分钟，供所有需要验证码的地方验证使用
        String redisKey = RedisKeyRules.verifyToken(sendCheck.getType(), sendCheck.getPhone());
        redisCommonService.delete(redisKey);
        redisStringService.add(redisKey, sendCheck.getToken(), 5 * 60L);
        log.info("REST request to send code. success! result: {}", result);
        return result;
    }

    /**
     * 图片上传识别
     */
    @PostMapping("/image")
    public Object image(@RequestBody @Validated PictureObjectDTO picture) throws CustomerException, JSONException {
        log.info("REST request to check image. image: {}", picture.getType());
        Object result = null;
        if(picture.getType().equals(CommonConstants.ID_CARD)){
            result = imageTextService.idCard(picture.getImage());
        }else if(picture.getType().equals(CommonConstants.BANK_CARD)){
            result = imageTextService.bankCard(picture.getImage());
        }
        log.info("REST request to check image. success! result: {}", result);
        return result;
    }

    /**
     * 获取百度识别token
     */
    @PostMapping("/token")
    public CommonTokenDTO token(@RequestHeader(header_uid)Long userNo){
        log.info("REST request to get token. userNo: {}", userNo);
        CommonTokenDTO result = imageTextService.token();
        log.info("REST request to get token. success! result: {}", result);
        return result;
    }

    /**
     * 设备端提供设备信息，用于推送
     */
    @PostMapping("/push/message")
    public void setPushInfo(@RequestHeader(header_uid)Long userNo, @RequestBody PushMessageDTO pushMessage) throws JsonProcessingException {
        log.info("REST request to set push info. userNo: {}, pushMessage: {}", userNo, pushMessage);
        commonService.setPushInfo(userNo, pushMessage);
        log.info("REST request to set push info. success!");
    }

    /**
     * 发起推送
     */
    @PostMapping("/push/start")
    public void startPushMessge(@RequestHeader(header_uid)Long userNo, @RequestBody PushMessageStartPushDTO startPush) throws JsonProcessingException {
        log.info("REST request to start push message. userNo: {}, startPush{}", userNo, startPush);
        startPush.setUserNo(userNo);
        commonService.startPush(startPush);
        log.info("REST request to start push message. success!");
    }
}

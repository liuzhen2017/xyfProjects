package com.xinyunfu.customer.service.checktoken;

import com.alibaba.fastjson.JSON;
import com.dingxianginc.ctu.client.CaptchaClient;
import com.dingxianginc.ctu.client.model.CaptchaResponse;
import com.xinyunfu.customer.constant.CommonConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Data
@Slf4j
@Service
@ConfigurationProperties(prefix = "customer.ding-xiang")
public class DingXiangCheckTokenService {

    private String appId;
    private String appSecret;
    private String host;

    protected static final Integer identify = CommonConstants.dingxiang;
    private static String path = "/api/tokenVerify";

    CaptchaClient captchaClient = null;

    @PostConstruct
    private void init(){
        captchaClient = new CaptchaClient(appId,appSecret);
        captchaClient.setCaptchaUrl(host + path);
        log.debug("appId: {}, appSecret: {}, host: {}", appId, appSecret, host);
    }

    public Boolean checkToken(String token, String ip) throws Exception {
        CaptchaResponse response = captchaClient.verifyToken(token);
        log.debug("response: {}", JSON.toJSON(response));
        return response.getResult();
    }
}

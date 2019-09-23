package com.xinyunfu.customer.service.checktoken;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.customer.constant.CommonConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

@Data
@Slf4j
@Service
@ConfigurationProperties(prefix = "customer.tencent")
public class TencentCheckTokenService {

    @Autowired private RestTemplate restTemplate;

    private String appId;
    private String appSecret;
    private String host;

    protected static final Integer identify = CommonConstants.tencent;
    private static String path = "/ticket/verify?aid=%s&AppSecretKey=%s&Ticket=%s&Randstr=%s&UserIP=%s";

    public Boolean checkToken(String token, String ip){

        String url = host + String.format(path, appId, appSecret, token, UUID.randomUUID().toString(), ip);

        log.debug("request url : {}", url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String body = response.getBody();
        log.info("tencent check token: {}", body);
        if(Objects.nonNull(body)) {
            JSONObject json = JSON.parseObject(body);
            return json.getInteger("response") == 1;
        }
        return false;
    }
}

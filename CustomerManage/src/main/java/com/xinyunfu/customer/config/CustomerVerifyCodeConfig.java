package com.xinyunfu.customer.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.xinyunfu.customer.constant.CommonConstants.*;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "customer.verify-code")
public class CustomerVerifyCodeConfig {

    private String login;
    private String register;
    private String resetPassword;
    private String resetPayPassword;
    private String bindPhone;
    private String unbindPhone;
    private Boolean send;
    private String defaultCode;

    private static Map<String, String> verifyMap = null;

    @PostConstruct
    private void init() {
        log.info("start init verify map ......");
        verifyMap = new HashMap<String, String>() {{
            put(LOGIN, login);
            put(REGISTER, register);
            put(RESET_PASSWORD, resetPassword);
            put(RESET_PAY_PASSWORD, resetPayPassword);
            put(BIND_PHONE, bindPhone);
            put(UNBIND_PHONE, unbindPhone);
        }};
        log.info("init verify map success!");

        defaultCode = defaultCode.toUpperCase();
        log.info("default referrer code: {}", defaultCode);
    }

    public String verifyCode(String type) {
        return verifyMap.get(type);
    }
}

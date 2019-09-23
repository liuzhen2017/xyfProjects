package com.xinyunfu.customer.service.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Data
@Service
@ConfigurationProperties("customer.auth-card")
public class AuthCardConfigService {
    private String appKey;
    private String appSecret;
    private String appCode;
    private String host;
}

package com.xinyunfu.customer.service.auth;

import com.xinyunfu.customer.dto.auth.BankCardAuthResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 银行卡认证功能
 */
@Service
public class BankCardAuthService {
    @Autowired private RestTemplate restTemplate;
    @Autowired private AuthCardConfigService authCardConfigService;

    private static String path = "/bank3Check";
    private static String param = "?accountNo=%s&idCard=%s&name=%s";

    public BankCardAuthResultDTO auth(String accountNo, String idCard, String name){

        String host = authCardConfigService.getHost();
        String pathTemp = host + path + param;

        String reqPath = String.format(pathTemp, accountNo, idCard, name);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPCODE " + authCardConfigService.getAppCode());

        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<BankCardAuthResultDTO> resp = restTemplate.exchange(reqPath, HttpMethod.GET, entity, BankCardAuthResultDTO.class);

        return resp.getBody();
    }
}

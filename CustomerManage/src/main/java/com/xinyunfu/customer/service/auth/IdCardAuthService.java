package com.xinyunfu.customer.service.auth;

import com.xinyunfu.customer.dto.auth.IdCardAuthResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 身份证认证功能
 */
@Service
public class IdCardAuthService {

    @Autowired private RestTemplate restTemplate;
    @Autowired private AuthCardConfigService authCardConfigService;

    private static String path = "/IDCard";
    private static String param = "?idCard=%s&name=%s";

    public IdCardAuthResultDTO auth(String idCard, String name){
        String host = authCardConfigService.getHost();
        String pathTemp = host + path + param;

        String reqPath = String.format(pathTemp, idCard, name);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPCODE " + authCardConfigService.getAppCode());

        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<IdCardAuthResultDTO> resp = restTemplate.exchange(reqPath, HttpMethod.GET, entity, IdCardAuthResultDTO.class);

        return resp.getBody();
    }
}

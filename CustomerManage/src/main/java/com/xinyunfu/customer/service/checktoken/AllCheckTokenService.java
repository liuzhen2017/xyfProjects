package com.xinyunfu.customer.service.checktoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllCheckTokenService {

    @Autowired private DingXiangCheckTokenService dingXiangCheckTokenService;
    @Autowired private TencentCheckTokenService tencentCheckTokenService;

    public Boolean checkToken(String token, Integer tokenType, String ip) throws Exception {
        if(tokenType.equals(DingXiangCheckTokenService.identify))
            return dingXiangCheckTokenService.checkToken(token, ip);
        return tencentCheckTokenService.checkToken(token, ip);
    }
}

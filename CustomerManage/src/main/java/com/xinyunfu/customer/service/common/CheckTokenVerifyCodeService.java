package com.xinyunfu.customer.service.common;

import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.exception.CustomerExceptionEnum;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisKeyRules;
import com.xinyunfu.customer.service.redis.RedisStringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckTokenVerifyCodeService {
    @Autowired private RedisStringService redisStringService;
    @Autowired private RedisCommonService redisCommonService;

    public void checkToken(String verifyType, String phone, String token) throws CustomerException {
        //判断token是否在redis中存在
        String redisTokenKey = RedisKeyRules.verifyToken(verifyType, phone);
        log.debug("redis token key: {}", redisTokenKey);
        if(!redisCommonService.exists(redisTokenKey)) throw new CustomerException(CustomerExceptionEnum.USER_REGISTER_TOKEN);

        String redisToken = (String) redisStringService.get(redisTokenKey);
        log.debug("token: {}", token);
        if(!redisToken.equals(token))
            throw new CustomerException(CustomerExceptionEnum.USER_REGISTER_TOKEN);
    }
}

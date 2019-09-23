package com.xinyunfu.service.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisCommonService {
    @Autowired private RedisTemplate redisTemplate;

    public void delete(String key){
        redisTemplate.delete(key);
    }

    public Boolean exists(String key){
        return redisTemplate.hasKey(key);
    }

    public void expire(String key, Long time){
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }
}

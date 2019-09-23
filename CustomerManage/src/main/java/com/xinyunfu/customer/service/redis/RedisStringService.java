package com.xinyunfu.customer.service.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisStringService {

    @Autowired private RedisTemplate redisTemplate;

    public void add(String key, Object value){
        if(Objects.isNull(value)) return;
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, new Random().nextInt(30) + 30, TimeUnit.MINUTES);
    }

    public void add(String key, Object value, Long offset){
        if(Objects.isNull(value)) return;
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, offset, TimeUnit.SECONDS);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }
}

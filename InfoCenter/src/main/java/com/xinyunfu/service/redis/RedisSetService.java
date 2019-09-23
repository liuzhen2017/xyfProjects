package com.xinyunfu.service.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisSetService {
    @Autowired private RedisTemplate redisTemplate;

    public void add(String key, Object... obj){
        Boolean hasKey = redisTemplate.hasKey(key);
        redisTemplate.opsForSet().add(key, obj);
        if(!hasKey)//第一次写入redis
            redisTemplate.expire(key, new Random().nextInt(30)+30, TimeUnit.MINUTES);
    }

    public void addList(String key, List<Long> objList){
        log.debug("objList:{}", objList);
        if(Objects.isNull(objList) || objList.isEmpty()) return;
        this.add(key, objList.toArray());
    }

    public void addStrList(String key, List<String> objList){
        log.debug("objList:{}", objList);
        if(Objects.isNull(objList) || objList.isEmpty()) return;
        this.add(key, objList.toArray());
    }

    public void remove(String key, Object... obj){
        redisTemplate.opsForSet().remove(key, obj);
    }

    public Set<Object> get(String key){
        return redisTemplate.opsForSet().members(key);
    }
}

package com.xinyunfu.customer.service.address;

import com.xinyunfu.customer.domain.address.CustAreaDTO;
import com.xinyunfu.customer.repository.address.CustAreaMapper;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisKeyRules;
import com.xinyunfu.customer.service.redis.RedisSetService;
import com.xinyunfu.customer.service.redis.RedisStringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AreaManagerService {

    @Autowired private CustAreaMapper custAreaMapper;
    @Autowired private RedisStringService redisStringService;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private RedisSetService redisSetService;

    public List<CustAreaDTO> getLevelArea(Integer areaLevel){
        return this.getLevelAreaId(areaLevel).stream().map(id -> this.findById(id))
                .collect(Collectors.toList());
    }
    public List<CustAreaDTO> getLowerArea(Long parentId){
        return this.getLowerAreaId(parentId).stream().map(id -> this.findById(id))
                .collect(Collectors.toList());
    }

    public List<Long> getLevelAreaId(Integer areaLevel){
        String redisKey = RedisKeyRules.levelArea(areaLevel);
        if(redisCommonService.exists(redisKey)){
            return redisSetService.get(redisKey).stream().map(var -> Long.valueOf(var.toString()))
                    .collect(Collectors.toList());
        }
        List<Long> keys = custAreaMapper.findIdByAreaLevel(areaLevel);
        if(Objects.isNull(keys) || keys.isEmpty()) return new ArrayList<>();

        redisSetService.addList(redisKey, keys);
        return keys;
    }
    public List<Long> getLowerAreaId(Long parentId){
        String redisKey = RedisKeyRules.lowerArea(parentId);
        if(redisCommonService.exists(redisKey)){
            return redisSetService.get(redisKey).stream().map(var -> Long.valueOf(var.toString()))
                    .collect(Collectors.toList());
        }
        List<Long> keys = custAreaMapper.findIdByParentId(parentId);
        if(Objects.isNull(keys) || keys.isEmpty()) return new ArrayList<>();

        redisSetService.addList(redisKey, keys);
        return keys;
    }

    public CustAreaDTO findById(Long id){
        String redisKey = RedisKeyRules.area(id);
        if(redisCommonService.exists(redisKey)){
            return (CustAreaDTO) redisStringService.get(redisKey);
        }

        CustAreaDTO area = custAreaMapper.findById(id);
        redisStringService.add(redisKey, area);
        return area;
    }
}

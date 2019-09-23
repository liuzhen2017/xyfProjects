package com.xinyunfu.service.pushtemp;

import com.xinyunfu.dto.Page;
import com.xinyunfu.dto.pushtemp.PushTemplateAddDTO;
import com.xinyunfu.dto.pushtemp.PushTemplateUpdateDTO;
import com.xinyunfu.entity.InfoCenterPushTemplateDTO;
import com.xinyunfu.mapper.InfoCenterPushTemplateMapper;
import com.xinyunfu.service.redis.RedisCommonService;
import com.xinyunfu.service.redis.RedisKeyRules;
import com.xinyunfu.service.redis.RedisStringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PushTemplateMangerService {
    @Autowired private InfoCenterPushTemplateMapper infoCenterPushTemplateMapper;
    @Autowired private RedisStringService redisStringService;
    @Autowired private RedisCommonService redisCommonService;

    public void addTemplate(PushTemplateAddDTO pushTemplateAdd){
        infoCenterPushTemplateMapper.insert(new InfoCenterPushTemplateDTO(pushTemplateAdd));
    }

    public InfoCenterPushTemplateDTO findByTemplateName(String templateName){
        String redisKey = RedisKeyRules.pushTemplate(templateName);
        if(redisCommonService.exists(redisKey)){
            return (InfoCenterPushTemplateDTO) redisStringService.get(redisKey);
        }
        InfoCenterPushTemplateDTO template = infoCenterPushTemplateMapper.findByTemplateName(templateName);
        redisStringService.add(redisKey, template);
        return template;
    }

    public Page<InfoCenterPushTemplateDTO> pageQuery(Integer pageNo, Integer pageSize){
        List<String> templateList = infoCenterPushTemplateMapper.findLimit(pageNo, pageSize);
        Integer totalCount = infoCenterPushTemplateMapper.count();
        return new Page<>(pageNo, pageSize, totalCount,
                templateList.stream().map(var -> this.findByTemplateName(var)).collect(Collectors.toList()));
    }

    public void update(PushTemplateUpdateDTO pushTemplateUpdate){
//        infoCenterPushTemplateMapper.update(new InfoCenterPushTemplateDTO(pushTemplateUpdate));
    }
}

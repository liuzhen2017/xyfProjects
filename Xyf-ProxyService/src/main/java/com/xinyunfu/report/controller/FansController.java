package com.xinyunfu.report.controller;

import com.xinyunfu.report.dto.*;
import com.xinyunfu.report.service.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author xxb
 * @date 2019/9/3
 * @Description :
 */
@RestController
@RequestMapping("fansdata")
@Slf4j
public class FansController {
    @Autowired
    FansService fansService;
    /**
     * 获取用户注册和推荐排名
     * @return
     */
    @PostMapping("/reportBar")
    public Map<String,Object>findUserRegistAndrefer(){
        Map<String, Object> map=new TreeMap<>();
        try{
            map= fansService.queryFansBarData();
            return map;
        }catch(Exception e){
            log.error("=====请求异常=，msg={}",e.getMessage());
        }
        return map;
    }
}

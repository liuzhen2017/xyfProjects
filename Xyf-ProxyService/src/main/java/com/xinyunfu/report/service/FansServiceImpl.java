package com.xinyunfu.report.service;

import com.xinyunfu.report.dao.proxy.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * @author XRZ
 * @date 2019/9/3
 * @Description :
 */
@Service
@Slf4j
public class FansServiceImpl implements FansService {
    @Autowired
    private FansMapper fansMapper;
    @Autowired
    private IEchartsFactoryService echartsFactoryService;
    @Override
    /**
     * 获取粉丝数量 推荐排名
     * @return
     */
    public Map<String, Object> queryFansBarData() throws Exception {
        List<Map<String, Object>> listUserInfo = fansMapper.selectFansList();
        Map<String, Object> map=new TreeMap<>();
        List<Map<String, Object>> source[] = new ArrayList[1];
        source[0]=listUserInfo;
        String title[]=new String[]{"粉丝推荐量"};
        try {
            map=echartsFactoryService.createdBar(source, title);
            return map;
        } catch (Exception e) {
            log.error("queryFansBarData is Error,Msg =", e.getMessage(), e);
            return map ;
        }
    }
}

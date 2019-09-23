package com.xinyunfu.report.service;


import com.baomidou.mybatisplus.extension.service.impl.*;
import com.xinyunfu.report.dao.proxy.CusUserMapper;
import com.xinyunfu.report.model.*;
import com.xinyunfu.report.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xxb
 * @date 2019/9/2
 * @Description :
 */
@Service
@Slf4j
public class CusUserServiceImpl  implements UserService {

  @Autowired
  private CusUserMapper cusUserMapper;
  @Autowired
  private IEchartsFactoryService echartsFactoryService;

    /**
     *   用户注册折线图
     */
    @Override
    public Map<String,Object> queryUserLineData() throws Exception {
        List<Map<String, Object>> listUserInfo = cusUserMapper.getUserRegistCount();
        List<Map<String, Object>> source[] = new ArrayList[1];
        Map<String, Object> map=new HashMap<>();
        source[0]=listUserInfo;
        String title[]=new String[]{"注册量"};
        try {
            return echartsFactoryService.createdLine(source, title);
        } catch (Exception e) {
            log.error("queryUserLineData is Error,Msg =", e.getMessage(), e);
            return map ;
        }
    }
    /**
     *   用户注册饼图
     */
    @Override
    public Map<String,Object> queryUserPieData() throws Exception {
        List<Map<String, Object>> listUserInfo = cusUserMapper.getUserRegistCount();
        Map<String, Object> map=new HashMap<>();
        String title[]=new String[]{"用户注册量"};
        try {
            return echartsFactoryService.createdPie(listUserInfo, title);
        } catch (Exception e) {
            log.error("queryUserPieData is Error,Msg =", e.getMessage(), e);
            return map ;
        }
    }
    /**
     * 查询用户列表
     *
     * @param custUser 用户信息
     * @return 用户集合
     */
    @Override
    public List<CustUser> selectUserList(CustUser user) {
        return cusUserMapper.selectCustUserList(user);
    }
}

package com.xinyunfu.report.service;

import java.util.*;

/**
 * @author XRZ
 * @date 2019/9/3
 * @Description :
 */
public interface FansService {

    /**
     * 获取粉丝数量 推荐排名柱状图
     * @return
     */
    public Map<String,Object> queryFansBarData()throws Exception;
}

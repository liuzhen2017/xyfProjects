package com.xinyunfu.report.service;

import com.baomidou.mybatisplus.extension.service.*;
import com.xinyunfu.report.model.*;

import java.util.List;
import java.util.Map;

/**
 * @author xb
 * @date 2019/9/2
 * @Description :
 */

public interface UserService{

    /**
     * 根据创建时间获取注册用户 注册量折线图
     * @return
     */
    public Map<String,Object> queryUserLineData()throws Exception;

    /**
     * 根据创建时间获取注册用户 注册量饼图
     * @return
     */
    public Map<String,Object> queryUserPieData()throws Exception;


    /**
     * 查询用户列表
     *
     * @param user 用户信息
     * @return 用户集合
     */
    public List<CustUser> selectUserList(CustUser user);
}

package com.xinyunfu.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.*;
import com.baomidou.mybatisplus.core.metadata.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import com.ruoyi.common.core.controller.*;
import com.ruoyi.common.core.page.*;
import com.xinyunfu.report.model.*;
import com.xinyunfu.report.service.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xinyunfu.jace.utils.ResponseInfo;

import javax.xml.ws.*;
import java.util.*;


/**
 * @author XRZ
 * @date 2019/9/2
 * @Description :
 */
@RestController
@RequestMapping("userdata")
@Slf4j
public class UserDataController extends BaseController {
    @Autowired
    UserService userService;

    /**
     * 导出line图表
     *
     * @return
     */
    @RequestMapping("reportLine")
    public Map<String, Object> reportUserRegistLine() {
        Map<String, Object> userregistmap = new HashMap<>();
        try {
            userregistmap = userService.queryUserLineData();
            return userregistmap;
        } catch (Exception e) {
            log.error("==请求异常=,msg={}", e.getMessage());
        }
        return userregistmap;
    }

    /**
     * 导出line图表
     *
     * @return
     */
    @RequestMapping("reportPie")
    public Map<String, Object> reportUserRegistPie() {
        Map<String, Object> userregistmap = new HashMap<>();
        try {
            userregistmap = userService.queryUserPieData();
            return userregistmap;
        } catch (Exception e) {
            log.error("==请求异常=,msg={}", e.getMessage());
        }
        return userregistmap;
    }


    /**
     * 查询用户列表
     */
    @RequestMapping("/queryUserRegistPage")
    public List<CustUser> selectUserRegistList(CustUser user){
        return userService.selectUserList(user);
    }


}

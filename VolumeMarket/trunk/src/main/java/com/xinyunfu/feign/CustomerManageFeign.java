package com.xinyunfu.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xinyunfu.customer.dto.user.UserInfoDTO;
import com.xinyunfu.jace.utils.ResponseInfo;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/10
 */                 //CustomerManage 
@FeignClient(value = "CustomerManage",decode404=false)
public interface CustomerManageFeign {

    /**
     * 获取用户信息
     * @param userNo
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/customer/inner/user/info")
    public ResponseInfo<UserInfoDTO> getUserInfo(@RequestParam("userNo")  Long userNo);
    
    
    /**
     * 获取推荐关系表
     * @param json
     * @return
     */
    @GetMapping(value="/customer/inner/user/referral",produces="application/json;charset=UTF-8")
    public ResponseInfo<List> getPushLinked(@RequestParam("userNo")long userNo) ;
    
    /**
     * 响应结果，true表示当前用户有两个子推荐人，false表示没有
     * @param json
     * @return
     */
    @GetMapping(value="/customer/inner/user/relation",produces="application/json;charset=UTF-8")
    public ResponseInfo<Boolean> relation(@RequestParam("userNo")long userNo) ;
}

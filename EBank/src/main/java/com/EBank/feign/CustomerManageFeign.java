package com.EBank.feign;

import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/10
 */
@FeignClient(value = "${customer-manage.name}")
@RequestMapping("/customer/")
public interface CustomerManageFeign {
    /**
     * 获取用户卡号
     * @param userNo
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "inner/bank/account/all")
    public String queryAccount(@RequestParam  String userNo);

    /**
     * 获取用户信息
     * @param userNo
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "inner/user/info")
    public String getUserInfo(@RequestParam  String userNo);
}

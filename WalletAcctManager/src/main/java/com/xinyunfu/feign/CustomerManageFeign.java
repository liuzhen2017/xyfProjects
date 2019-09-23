package com.xinyunfu.feign;


import com.xinyunfu.dto.UserInfoDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author XRZ
 * @date 2019/7/15
 * @Description : 客户端管理系统
 */
@FeignClient("CustomerManage")
public interface CustomerManageFeign {

    @GetMapping("/customer/inner/user/info")
    public ResponseInfo<UserInfoDTO> userInfo(@RequestParam("userNo") Long userNo);

}

package com.xinyunfu.fegin;


import com.xinyunfu.dto.SendCodeDTO;
import com.xinyunfu.dto.UserRegisterDTO;
import com.xinyunfu.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author XRZ
 * @date 2019/7/15
 * @Description : 客户端管理系统
 */
@FeignClient("CustomerManage")
public interface CustomerManageFeign {

    /**
     * 根据地址ID获取收货信息
     * @param id 地址ID
     * @return ResponseInfo<ShippingAddressInfoDTO>
     */
    @PostMapping("/customer/user/register")
    ResponseInfo<Object> register(@RequestBody UserRegisterDTO dto);


    /**
     * 发送验证码
     * @param dto
     * @return
     */
    @PostMapping("/customer/common/code")
    ResponseInfo<Object> code(@RequestBody SendCodeDTO dto,@RequestHeader("X-Real-IP") String ip);

}

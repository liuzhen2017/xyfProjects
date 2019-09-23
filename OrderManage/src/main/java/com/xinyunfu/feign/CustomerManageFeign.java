package com.xinyunfu.feign;

import com.xinyunfu.dto.customer.ShippingAddressInfoDTO;
import com.xinyunfu.dto.customer.UserInfoDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    @GetMapping("/customer/inner/address")
    ResponseInfo<ShippingAddressInfoDTO> getAddress(@RequestParam("id") Long id);

    /**
     * 获取用户信息
     * @param userNo
     * @return
     */
    @GetMapping("/customer/inner/user/info")
    ResponseInfo<UserInfoDTO> getUserInfo(@RequestParam("userNo") Long userNo);


    /**
     * 修改用户购买套餐的数量
     * @param userNo
     * @param mealNu
     */
    @GetMapping("/customer/inner/user/extension/meal")
    ResponseInfo<Object> meal(@RequestParam("userNo") Long userNo, @RequestParam("mealNu") Integer mealNu);

    /**
     * 根据当前用户的编号，获取当前用户的10个推荐人
     * @param userNo 用户编号
     * @return
     */
    @GetMapping("/customer/inner/user/referral")
    ResponseInfo<List<String>> referral(@RequestParam("userNo") Long userNo);
}

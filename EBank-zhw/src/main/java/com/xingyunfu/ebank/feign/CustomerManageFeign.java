package com.xingyunfu.ebank.feign;

import com.rnmg.jace.utils.ResponseInfo;
import com.xingyunfu.ebank.dto.user.BankAccountInfoDTO;
import com.xingyunfu.ebank.dto.user.UserInfoDTO;
import com.xingyunfu.ebank.dto.user.UserPhoneListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "CustomerManage")
@RequestMapping("/customer/inner")
public interface CustomerManageFeign {
    /**
     * 获取用户卡号
     */
    @GetMapping("/bank/account/all")
    ResponseInfo<List<BankAccountInfoDTO>> queryAccount(@RequestParam Long userNo);

    /**
     * 获取用户信息
     */
    @GetMapping("/user/info")
    ResponseInfo<UserInfoDTO> getUserInfo(@RequestParam Long userNo);

    /**
     * 根据手机号查询用户信息
     */
    @PostMapping("/user/phone")
    ResponseInfo<List<UserInfoDTO>> findByPhoneList(@RequestBody UserPhoneListDTO phoneList);
}

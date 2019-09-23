package com.xinyunfu.customer.feigin;

import com.rnmg.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("WalletAcctManage")
public interface WalletAcctManageFeign {

    /**
     * 用户注册同步至wallet
     */
    @GetMapping("/userWallet/createAccount")
    ResponseInfo<Object> createAccount(@RequestParam String userNo, @RequestParam String userName,
                                       @RequestParam String userType, @RequestParam String accountType);
}

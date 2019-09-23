package com.xingyunfu.ebank.feign;

import com.rnmg.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("WalletAcctManage")
public interface WalletAcctManageFeign {

    /**
     * 现金消费
     */
    @GetMapping("/userWallet/consume")
    ResponseInfo<Object> consume(@RequestParam String userNo, @RequestParam String orderNo, @RequestParam BigDecimal money);
}

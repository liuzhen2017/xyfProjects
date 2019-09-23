package com.xinyunfu.feign;

import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author XRZ
 * @date 2019/9/18
 * @Description : 用户钱包
 */
@FeignClient("WalletAcctManage")
public interface UserWalletFeign {

    /**
     * 给指定账户 发红包 （分润）
     * @param str  用户编号;金额,用户编号;金额
     * @return
     */
    @GetMapping("/redEnvelopes")
    ResponseInfo<Object> redEnvelopes(@RequestParam("str") String str);

}

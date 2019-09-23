package com.xinyunfu.customer.feigin;

import com.rnmg.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("VolumeMarket")
@RequestMapping("/")
public interface VolumeMarket {
    /**
     * 需要预先设置生成节点. 如新用户注册的节点和邀请用户注册的节点
     */
    @GetMapping("couponManger/giveByNode")
    ResponseInfo<String> giveByNode(@RequestParam String userNo, @RequestParam String couponNode);

    @GetMapping("couponManger/registGiveCoupon")
    ResponseInfo<String> registGiveCoupon(@RequestParam Long userNo, @RequestParam Long recommendUserNo);
}

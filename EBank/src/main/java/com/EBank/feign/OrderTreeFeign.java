package com.EBank.feign;

import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/10
 */
@FeignClient(value = "${order-three.name}")
@RequestMapping("/api/")
public interface OrderTreeFeign {
    /*
     * 通知订单树->新的订单
     */
    @RequestMapping(value="/joinOrderTree", method= RequestMethod.GET)
    ResponseInfo<String> joinOrderTree(@RequestParam String orderNo,@RequestParam String userNo,@RequestParam Integer userType,@RequestParam Integer count);
}

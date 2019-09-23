package com.xinyunfu.feign;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/17
 * @Description : 券集市服务
 */
@FeignClient("VolumeMarket")
public interface VolumeMarketFeign {

    /**
     * 答题通知券集市
     * @param object
     * @return
     */
    @PostMapping("/couponManger/activationCoupon")
    ResponseInfo<Object> activationCoupon(@RequestBody JSONObject object);
}

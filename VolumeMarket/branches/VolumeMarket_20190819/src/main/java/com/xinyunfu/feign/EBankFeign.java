package com.xinyunfu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.jace.utils.ResponseInfo;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/10
 */
@FeignClient(value = "EBank")
@RequestMapping("/ebank/")
public interface EBankFeign {
	@PutMapping("transfer")
    public ResponseInfo<String> transfer(@RequestBody JSONObject json);
}

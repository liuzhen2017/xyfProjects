package com.xinyunfu.sao;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xinyunfu.jace.utils.ResponseInfo;

@FeignClient(value="CustomerManage")
public interface CustomerManageSao {

	
	/**
	 * 获取推荐关系表
	 * @param json
	 * @return
	 */
    @GetMapping(value="/customer/inner/user/referral",produces="application/json;charset=UTF-8")
	public ResponseInfo<List> getPushLinked(@RequestParam("userNo")long userNo) ;
	
}

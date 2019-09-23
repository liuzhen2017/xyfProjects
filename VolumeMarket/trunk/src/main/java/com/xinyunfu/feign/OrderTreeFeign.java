package com.xinyunfu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.jace.utils.ResponseInfo;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/10
 */
@FeignClient(value = "OrderTree")
@RequestMapping("/api/")
public interface OrderTreeFeign {
	/*
	 * 通知订单树->答题完毕,进行转让
	 */
	/**
	 * 提交转让
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("assignment")
	public ResponseInfo<String> assignment(@RequestParam("order") String order, @RequestParam("price") int price);

	/**
	 * 查询是否可以购买
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("allowJoinTree")
	public ResponseInfo<String> allowJoinTree(@RequestBody JSONObject json);

	/**
	 * 通知订单树->新的订单
	 */
	@RequestMapping(value = "/joinOrderTree", method = RequestMethod.GET)
	ResponseInfo<String> joinOrderTree(@RequestParam String orderNo, @RequestParam String userNo,
			@RequestParam Integer userType, @RequestParam Integer count);

}

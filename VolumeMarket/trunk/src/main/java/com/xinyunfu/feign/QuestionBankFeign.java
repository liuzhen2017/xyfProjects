package com.xinyunfu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.xinyunfu.jace.utils.ResponseInfo;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/10
 */
@FeignClient(value = "QuestionBank")
public interface QuestionBankFeign {
	/**
	 * 查询是否已经答题完毕
	 * @param orderNo
	 * @return
	 */
	@PostMapping("/getUserCouponInfo/{currentUserId}")
	public ResponseInfo<String> getUserCouponInfo(@PathVariable("currentUserId") String currentUserId);

}

package com.xinyunfu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.sao.CustomerManageSao;
import com.xinyunfu.service.impl.UserPackageOrderServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jace
 * @since 2019-07-04
 */
@RestController
@Slf4j
public class OrderTreeController {

	
	@Autowired
	private UserPackageOrderServiceImpl userPushService;
	
	@Autowired
	private CustomerManageSao sao;
	
	
	/**
	 * 	提交订单树
	 * @param json
	 * @return
	 */
	@GetMapping("/api/joinOrderTree")
	public ResponseInfo<String> joinOrderTree(@RequestParam("userNo")String userNo,@RequestParam("count")int packageCount,@RequestParam("orderNo")String orderNo,@RequestParam("userType")String userType){
		try {
			return userPushService.insertOne(userNo, packageCount, orderNo,userType);
		}catch (Exception e) {
			return new ResponseInfo<String>("0001","提交订单树失败",null);
		}
		
	}
	
	
	
	
	/**
	 *	提交转让
	 * @param json
	 * @return
	 */
	@PostMapping("/api/assignment")
	public ResponseInfo<String> assignment(@RequestParam("order") String order){
		
		return userPushService.assignment(order);
	}
	


}

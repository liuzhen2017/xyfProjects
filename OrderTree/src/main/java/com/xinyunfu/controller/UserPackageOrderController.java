package com.xinyunfu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.impl.UserPackageOrderServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jace
 * @since 2019-07-06
 */
@RestController
@Slf4j
public class UserPackageOrderController {

	
	
	@Autowired
	UserPackageOrderServiceImpl service;
	
	
	
	@PostMapping("/api/allowJoinTree")
	public ResponseInfo<String> allowJoinTree(@RequestBody JSONObject json){
		
		log.info("----------------allowJoinTree是否准入订单树，检查执行-------------");
		return service.allowJoinTree(json.getString("userNo"),json.getString("userType"));
	}
    
	
	
	/**
	 * 	查询有效的节点数
	 * @param json
	 * @return
	 */
	@PostMapping("/api/getVildNodeCount")
	public ResponseInfo<String> getVildNodeCount(@RequestBody JSONObject json){
		
		log.info("查询剩余节点数："+json.toJSONString());
		try {
			ResponseInfo<String> res = service.getVildNodeCount(json);
			return res;
		} catch (Exception e) {
			return ResponseInfo.error("系统开小差了，请您稍后再试");
		}
		
	}
	
	
	
}

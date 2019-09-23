package com.xinyunfu.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyunfu.Constant.SysConstant;
import com.xinyunfu.entity.CouponList;
import com.xinyunfu.entity.CouponManger;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.CouponMangerService;
import com.xinyunfu.service.CouponMarketService;

/**
 * @author liuzhen 优惠券管理 Controller
 * 
 * 
 */
@RestController
@RequestMapping("/couponManger/")
public class CouponMangerController {
	@Autowired
	private CouponMangerService couponMangerService;
	@Autowired
	private CouponMarketService couponMarketService;

	/**
	 * 优惠券管理分页列表
	 *
	 * @param request 优惠券管理实体
	 * @return ResponseInfo
	 */
	@PostMapping(value = "queryList.do")
	public ResponseInfo<IPage<CouponManger>> list(String parmat) {
		JSONObject jb =new JSONObject(); JSONObject.parseObject(parmat);
		jb.put("page", 1);
		jb.put("pageSize", 10);
		CouponManger entity = new CouponManger();//parseObject(parmat, CouponManger.class);
		Integer page = jb.getInteger("page");
		Integer pageSize = jb.getInteger("pageSize");
		return  couponMangerService.list(entity, page, pageSize);		
	}
	/**
	 * 优惠券管理分页列表
	 *
	 * @param request 优惠券管理实体
	 * @return ResponseInfo
	 */
	@PostMapping(value = "queryLists.do")
	public ResponseInfo<IPage<CouponManger>> queryList(@RequestBody String parmat) {
		JSONObject jb = JSONObject.parseObject(parmat);
		CouponManger entity = JSONObject.parseObject(parmat, CouponManger.class);
		Integer page = jb.getInteger("page");
		Integer pageSize = jb.getInteger("pageSize");
		return couponMangerService.list(entity, page, pageSize);
	}

	/**
	 * 可购买优惠券列表
	 *
	 * @param request 优惠券管理实体
	 * @return ResponseInfo
	 */
	@PostMapping(value = "queryCanBuyCou")
	public ResponseInfo<IPage<CouponManger>> queryCanBuyCou(
			@RequestParam(defaultValue = "1", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer pageSize) {
		CouponManger entity = new CouponManger();
		entity.setIsCanBuy("Y");
		return couponMangerService.list(entity, page, pageSize);
	}

	/**
	 * 购买优惠券
	 * 
	 * @param userNo   用户编号
	 * @param couponid 优惠券Id
	 * @param num      数量
	 * @return
	 */
	@PostMapping(value = "buyCoupon")
	public ResponseInfo<String> buyCoupon(@RequestBody String json) {
		JSONObject jb =JSONObject.parseObject(json);
		String userNo =jb.getString("userNo");
		String couponid =jb.getString("couponid");
		int num =jb.getIntValue("num");
		return couponMangerService.buyCoupon(userNo, couponid, num);
	}

	/**
	 * 优惠券管理根据id查询详情
	 *
	 * @param id 主键id
	 * @return ResponseInfo
	 */
	@GetMapping("/queryById")
	public ResponseInfo<CouponManger> queryById(@RequestParam Long id) {
		return couponMangerService.queryById(id);
	}	

	/**
	 * 优惠券管理根据条件查询详情
	 *
	 * @return ResponseInfo
	 */
	@GetMapping("/query")
	public ResponseInfo<CouponManger> query(CouponManger entity) {
		return couponMangerService.query(entity);
	}

	/**
	 * 新增优惠券管理
	 *
	 * @param request 请求参数
	 * @return ResponseInfo
	 */
	@PostMapping("/add")
	public ResponseInfo<Boolean> add(@RequestBody CouponManger entity) {
		return couponMangerService.add(entity);
	}

	/**
	 * 更新优惠券管理
	 *
	 * @param request 请求参数
	 * @param id      主键id
	 * @return ResponseInfo
	 */
	@PutMapping("/update")
	public ResponseInfo<Boolean> update( @RequestBody CouponManger entity) {
		return couponMangerService.update(entity);
	}

	/**
	 * 删除优惠券管理
	 *
	 * @param id 主键id
	 * @return ResponseInfo
	 */
	@DeleteMapping("/delete")
	public ResponseInfo<Boolean> delete(@PathVariable Long id) {
		return couponMangerService.delete(id);
	}

	/**
	 * 系统按节点赠送优惠券
	 * 
	 * @param userNo     用户编号
	 * @param couponNode 节点信息 注册regist,推荐recommend
	 * @return
	 */
	@GetMapping(value = "giveByNode")
	public ResponseInfo<String> giveCoupon(@RequestParam String userNo, @RequestParam String couponNode,String orderNo) {
		return couponMangerService.giveCoupon(userNo, couponNode,orderNo);
	}
	/**
	 * 系统按节点赠送优惠券
	 * 
	 * @param userNo     用户编号
	 * @param couponNode 节点信息 注册regist,推荐recommend
	 * @return
	 */
	@GetMapping(value = "registGiveCoupon")
	public ResponseInfo<String> registGiveCoupon(@RequestParam(required=true) String userNo,@RequestParam(required=false)String recommendUserNo) {
		
		 couponMangerService.giveCoupon(userNo, SysConstant.coupon_Node_regist,null);
		if(!StringUtils.isEmpty(recommendUserNo)) {
			couponMangerService.giveCoupon(recommendUserNo, SysConstant.coupon_Node_recommend,null);  
		}
		return ResponseInfo.success("");
	}
	/**
	 * 套餐购买成功后,配置18张券
	 * 
	 * @param userNo  用户编号
	 * @param orderNo 订单号
	 * @return
	 */
	@GetMapping(value = "distriCouonByOrderNo")
	public ResponseInfo<String> distriCoupon(@RequestParam String userNo,@RequestParam String orderNo,@RequestParam(value="nums",defaultValue="1") Integer nums) {
		return couponMangerService.distriCouon(userNo, orderNo,nums);
	}

	/**
	 * 答题成功后,激活优惠券
	 * 
	 * @param userNo       用户编号
	 * @param subjectIndex 题目序列号
	 * @return
	 */
	@RequestMapping(value = "activationCoupon", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseInfo<String> activationCoupon(@RequestBody String json) {
		JSONObject jb = JSONObject.parseObject(json);
		String userNo = jb.getString("userNo");
		String orderNo = jb.getString("orderNo");
		Integer subjectIndex = jb.getInteger("subjectIndex");
		return couponMangerService.updateByMarket(userNo, subjectIndex, SysConstant.coupon_status_no_activation05,
				orderNo);
	}

	@RequestMapping(value = "queryCuponMarket", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseInfo<String> queryCuponMarket(String str) {
		return couponMarketService.queryCuponMarket();
	}
	
	@PostMapping(value = "queryNewCuponMarket")
	public ResponseInfo<List<CouponList>> queryNewCuponMarket(@RequestHeader("currentUserId") String currentUserId){
		  return couponMangerService.queryNewCuponMarket(currentUserId);
		
	}

}
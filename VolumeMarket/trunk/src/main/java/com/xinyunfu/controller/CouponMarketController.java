package com.xinyunfu.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xinyunfu.customer.dto.user.UserInfoDTO;
import com.xinyunfu.entity.CouponMarket;
import com.xinyunfu.feign.CustomerManageFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.CouponMarketService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhen couponMarket Controller
 */
@RestController 
@RequestMapping("/couponMarket/")
@Slf4j
public class CouponMarketController {
	@Autowired
	private CouponMarketService couponMarketService;
	
    @Autowired
    CustomerManageFeign sao;
    @Autowired
	JmsMessagingTemplate jmsMessagingTemplate;
   	  

	/**
	 * couponMarket分页列表
	 *
	 * @param request couponMarket实体
	 * @return ResponseInfo
	 */
	@PostMapping("queryList")
	public ResponseInfo<IPage<CouponMarket>> list(@RequestHeader(name = "currentUserId") String currentUserId,@RequestBody String parmat) {
		JSONObject jb = JSONObject.parseObject(parmat);
		CouponMarket entity = JSONObject.parseObject(parmat, CouponMarket.class);
		Integer page = jb.getInteger("page");
		Integer pageSize = jb.getInteger("pageSize");
		// 如果为空，则取默认值
		if (StringUtils.isEmpty(entity.getUserNo())) {
			entity.setUserNo(currentUserId);
		}
		return couponMarketService.list(entity, page, pageSize);
	}

	@PostMapping(value = "queryByGroupBy")
	public ResponseInfo<Map<String, Object>> queryByGroupBy(@RequestHeader("currentUserId") String currentUserId,
			@RequestBody String parmat) {
		JSONObject jb = JSONObject.parseObject(parmat);
		String couponStatus =jb.getString("couponStatus");
		String beginDate =jb.getString("beginDate");
		String endDate =jb.getString("endDate");
		Integer page =jb.getInteger("page");
		Integer pageSize =jb.getInteger("pageSize");
		return couponMarketService.queryByGroupBy(currentUserId,couponStatus,beginDate,endDate,page,pageSize);
	}
	/**
	 * couponMarket分页列表
	 *
	 * @param request couponMarket实体
	 * @return ResponseInfo
	 */
	@PostMapping("queryByGroupList")
	public ResponseInfo<IPage<Map<String, Object>>> queryByGroupList(@RequestHeader(name = "currentUserId") String currentUserId,@RequestBody String parmat) {
		JSONObject jb = JSONObject.parseObject(parmat);
		Integer page = jb.getInteger("page");
		Integer pageSize = jb.getInteger("pageSize");
		String couponStatus =jb.getString("couponStatus");
		String beginDate =jb.getString("beginDate");
		String endDate =jb.getString("endDate");
		return couponMarketService.queryByGroupList(page, pageSize,currentUserId,couponStatus,beginDate,endDate);
	}

	
	/**
	 * couponMarket根据id查询详情
	 *
	 * @param id 主键id
	 * @return ResponseInfo
	 */
	@PostMapping("/queryById")
	public ResponseInfo<CouponMarket> queryById(@PathVariable Long id) {
		return couponMarketService.queryById(id);
	}

	/**
	 * couponMarket根据id查询详情
	 *
	 * @param id 主键id
	 * @return ResponseInfo
	 */
	@PostMapping("/queryByOrderNo")
	public ResponseInfo<CouponMarket> queryByOrderNo(@RequestBody String json) {
		JSONObject jb =JSONObject.parseObject(json);
		CouponMarket ket =new CouponMarket();
		ket.setOrderNo(jb.getString("orderNo"));
		ket.setId(jb.getLong("id"));
		return couponMarketService.query(ket);
	}
	/**
	 * couponMarket根据条件查询详情
	 *
	 * @return ResponseInfo
	 */
	@PostMapping("/query")
	public ResponseInfo<CouponMarket> query(CouponMarket entity) {
		return couponMarketService.query(entity);
	}

	/**
	 * 新增couponMarket
	 *
	 * @param request 请求参数
	 * @return ResponseInfo
	 */
	@PostMapping("/add")
	public ResponseInfo<Boolean> add(@Validated @RequestBody CouponMarket entity) {
		return couponMarketService.add(entity);
	}

	/**
	 * 更新couponMarket
	 *
	 * @param request 请求参数
	 * @param id      主键id
	 * @return ResponseInfo
	 */
	@PutMapping("/update")
	public ResponseInfo<Boolean> update(@PathVariable Long id, @RequestBody CouponMarket entity) {
		return couponMarketService.update(entity);
	}

	/**
	 * 删除couponMarket
	 *
	 * @param id 主键id
	 * @return ResponseInfo
	 */
	@DeleteMapping("/delete")
	public ResponseInfo<Boolean> delete(@PathVariable Long id) {
		return couponMarketService.delete(id);
	}

	/**
	 * 转让券
	 * 
	 * @param currentUserId
	 * @return
	 */
	@PostMapping("/signCoupon")
	public ResponseInfo<String> signCoupon(@RequestHeader("currentUserId") String currentUserId,@RequestBody String json) {
		 JSONObject jb=JSONObject.parseObject(json);
		 BigDecimal price  =jb.getBigDecimal("price");
		 if(price.compareTo(new BigDecimal(30)) == 1) {
			 return ResponseInfo.errorReturn("转让金额超出最大金额!");
		 }
		return couponMarketService.signCoupon(currentUserId, price);
	}
	/**
	 * 修改最终状态
	 * @param currentUserId
	 * @return
	 */
	@PostMapping("/updateSignCoupon")
	public ResponseInfo<String> updateSignCoupon(@RequestHeader("currentUserId") String currentUserId){
		return couponMarketService.updateSignCoupon(currentUserId);
	}
	/**
	 * 查询是否可以购买
	 * @param currentUserId
	 * @return
	 */
	@PostMapping("/queryIsCanBuy")
	public ResponseInfo<String> queryIsCanBuy(@RequestHeader("currentUserId") String currentUserId){
		ResponseInfo<UserInfoDTO> res = sao.getUserInfo(Long.parseLong(currentUserId));
		if(!res.isSuccess()) {
			return ResponseInfo.errorReturn(res.getMessage());
		}
	    log.info("===== >> get userInfo{}",res);
		return couponMarketService.queryIsCanBuy(res.getData(),currentUserId);
	}
	
	/**
	 * 查询是否可以购买
	 * @param currentUserId
	 * @return
	 */
	@GetMapping("/queryIsCanBuyByService")
	public ResponseInfo<String> queryIsCanBuyByService(@RequestParam String userNo){
		ResponseInfo<UserInfoDTO> res = sao.getUserInfo(Long.parseLong(userNo));
		if(!res.isSuccess()) {
			return ResponseInfo.errorReturn(res.getMessage());
		}
	    log.info("===== >> get userInfo{}",res);
		return couponMarketService.queryIsCanBuyByMS(res.getData(),userNo);
	}
	/**
	 * 交易
	 * @param recvUserNo 接收人
	 * @param channel 渠道
	 * @param sourceUserNo 接收人
	 * @param orderNo 订单编号
	 * @return
	 */
    @GetMapping("paymentByCoupon.do")
    public ResponseInfo<String> paymentByCoupon(@RequestParam("recvUserNo") String recvUserNo,@RequestParam("channel") String channel,@RequestParam("sourceUserNo") String sourceUserNo,@RequestParam("orderNo") String orderNo){
    	JSONObject json =new JSONObject();
		json.put("recvUserNo", recvUserNo);
		json.put("channel", channel);
		json.put("sourceUserNo", sourceUserNo);
		json.put("orderNo", orderNo);
		return couponMarketService.paymentByCoupon(recvUserNo,channel,sourceUserNo,orderNo);
    }
    
	/**
	 * 查询可以转让的券
	 * @param currentUserId
	 * @return
	 */
	@PostMapping("/queryCanSign")
	public ResponseInfo<String> queryCanSign(@RequestHeader("currentUserId") String currentUserId){
		return couponMarketService.queryCanSign(currentUserId);
	}
	/**
	 * 转账回调
	 * @param orderId 订单Id
	 * @param status 转账状态
	 * @return
	 */
	@GetMapping("/backTransferAccounts")
	public ResponseInfo<String> backTransferAccounts(@RequestParam("orderId")String orderId,@RequestParam("status")Integer status){
		return couponMarketService.backTransferAccounts(orderId,status);
	}
	/**
	 * 丢单通知
	 * @param loseUsers 丢失用户 分号;集合 
	 * @param userNo 用户信息,通知用户
	 * @return
	 */
	@GetMapping("/loseOrderNotify")
	public ResponseInfo<String> loseOrderNotify(@RequestParam("loseUsers")String loseUsers,@RequestParam("userNo")String userNo){
		JSONObject json =new JSONObject();
		json.put("loseUsers", loseUsers);
		json.put("userNo", userNo);
		//this.jmsMessagingTemplate.convertAndSend("VolumeMarket_loseOrder", JSONObject.toJSONString(json));
		couponMarketService.loseOrderNotify(loseUsers, userNo);
		return ResponseInfo.success("");
	}
}
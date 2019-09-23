package com.xinyunfu.handle;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.Constant.SysConstant;
import com.xinyunfu.service.CouponMarketService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class VolumeMaketMQHandle {

	@Autowired
	private CouponMarketService couponMarketService;
	@RabbitHandler
	@RabbitListener(bindings = @QueueBinding(
	        value = @Queue(name = "VolumeMarket_backTransfer"),
	        exchange = @Exchange(name = "Volume",type = ExchangeTypes.DIRECT),key="VolumeMarket_key"))
	public void backTransfer(String message) {
		try {
	    	log.info("========接收到付款回调请求=========message={}",message);
	    	 JSONObject json =JSONObject.parseObject(message);
	    	 couponMarketService.backTransferAccounts(json.getString("orderNo"),Integer.parseInt(SysConstant.coupon_status_use_ed01));
		}catch (Exception e) {
			log.error("========接收到付款回调请求错误=======,Msg={},e={}",e.getMessage(),e);
		}
	}
	
}

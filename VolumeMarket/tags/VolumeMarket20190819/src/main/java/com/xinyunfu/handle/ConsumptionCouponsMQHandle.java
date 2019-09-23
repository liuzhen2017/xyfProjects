//package com.xinyunfu.handle;
//
//import org.springframework.amqp.core.ExchangeTypes;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSONObject;
//import com.xinyunfu.service.CouponMarketService;
//
//import lombok.extern.slf4j.Slf4j;
//@Slf4j
//@Component
//public class ConsumptionCouponsMQHandle  {
//	
//	@Autowired
//	private CouponMarketService couponMarketService;
//	
//	@RabbitHandler
//	@RabbitListener(bindings = @QueueBinding(
//	        value = @Queue(name = "VolumeMarket_payment"),
//	        exchange = @Exchange(name = "VolumeMarket",type = ExchangeTypes.DIRECT)))
//	public void sendMsg(String message) {
//	    try {
//	    	log.info("========接收到消费券请求=========message={}",message);
//	    	 JSONObject json =JSONObject.parseObject(message);
//	    	 couponMarketService.paymentByCoupon(json.getString("recvUserNo"),json.getString("channel"),json.getString("sourceUserNo"),json.getString("orderNo"));
//		}catch (Exception e) {
//			log.error("========消费券错误=======,Msg={},e={}",e.getMessage(),e);
//		}
//	}
////	@RabbitHandler
////	@RabbitListener(bindings = @QueueBinding(
////	        value = @Queue(name = "VolumeMarket_loseOrder"),
////	        exchange = @Exchange(name = "VolumeMarket",type = ExchangeTypes.DIRECT)))
////	public void loseOrderNotify(String message) {
////	    try {
////	    	log.info("========接收到发送单通知=========message={}",message);
////	    	 JSONObject json =JSONObject.parseObject(message);
////	    	 couponMarketService.loseOrderNotify(json.getString("loseUsers"),json.getString("userNo"));
////		}catch (Exception e) {
////			log.error("========消费券错误=======,Msg={},e={}",e.getMessage(),e);
////		}
////	}
//	
//}

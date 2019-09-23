package com.xinyunfu.sao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="VolumeMarket")
public interface VolumeMarketSao {
	
	
	@GetMapping("/couponMarket/paymentByCoupon.do")
	public String payUser(@RequestParam("recvUserNo") String recvUserNo,@RequestParam("channel") String channel,@RequestParam("sourceUserNo") String sourceUserNo,@RequestParam("orderNo") String orderNo);



	 @GetMapping("/couponMarket/loseOrderNotify")
	 public String loseOrderNotify(@RequestParam("loseUsers")String loseUsers,@RequestParam("userNo")String userNo);
}

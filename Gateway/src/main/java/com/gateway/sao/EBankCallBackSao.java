package com.gateway.sao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="EBank")
public interface EBankCallBackSao {

	 /**
     * 	异步通知->入账回调
     * @param json
     * @return
     */
	@PostMapping("/ebank/callback/transfer")
    public String callbackAccountEntry(@RequestBody String json);
   
	
	
	/**
     * 	异步通知
     * @param json
     * @return
     */
    @PostMapping("/ebank/callback/pay")
    public String callbackPayment(@RequestBody String json);
	
}

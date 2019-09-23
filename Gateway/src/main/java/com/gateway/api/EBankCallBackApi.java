package com.gateway.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gateway.sao.EBankCallBackSao;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EBankCallBackApi {

	
	@Autowired
	private EBankCallBackSao sao;
	
	
	@PostMapping("/api/callbackAccountEntry")
	public String callbackAccountEntry(@RequestBody String str) {
        log.info("callbackAccountEntry，入参："+str);
		return sao.callbackAccountEntry(str);
	}
	
	
	 /**
     * 异步通知
     * @param json
     * @return
     */
    @PostMapping("/api/callbackPayment")
    public String callbackPayment(@RequestBody String json) {
    	log.info("callbackPayment，入参："+json);
    	return sao.callbackPayment(json);
    };
	
	
	
}

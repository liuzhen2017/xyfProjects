package com.xinyunfu.agent.handle;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinyunfu.service.SysSendMessagerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SendMsqMQHandle  {
	
	@Autowired
	private SysSendMessagerService sendMsgService;

	@RabbitHandler
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(name = "InfoCenter_sendSMS"),
			exchange = @Exchange(name = "InfoCenter",type = ExchangeTypes.DIRECT)))
	public void sendMsg(String message) {
	    try {
	    	log.info("========接收到发送短信请求=========message={}",message);
			sendMsgService.sendMsg(message);
		}catch (Exception e) {
			log.error("=========发送短信错误=======,Msg={},e={}",e.getMessage(),e);
		}
	}
}

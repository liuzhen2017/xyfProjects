package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayTests {

	@Autowired
	JmsMessagingTemplate jmsMessagingTemplate;
	@Test
	public void send() {
		// 简单对列的情况下routingKey即为Q名
		Map<String, Object> map =new HashMap<>();
		map.put("recvObject", "15818660647");
		map.put("sendType", "1");
		map.put("templateNo", "1");
		this.jmsMessagingTemplate.convertAndSend("InfoCenter_sendSMS", JSONObject.toJSONString(map));
		System.out.println("发送完毕");
	}

}

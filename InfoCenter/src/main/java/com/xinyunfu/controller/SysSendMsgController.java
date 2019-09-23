package com.xinyunfu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.SysSendMessagerService;

import lombok.extern.slf4j.Slf4j;

/** @author liuzhen
* @version 1.0
* @date 2019/7/6
*/
@RestController
@RequestMapping("/send/")
@Slf4j
public class SysSendMsgController {
	@Autowired
	JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
	private SysSendMessagerService sendMsgService;

	@PostMapping("checkSendMsg.do")
	public ResponseInfo<Boolean>  checkSendMsg(@RequestBody String  json) {
	
        JSONObject jb =JSONObject.parseObject(json);
        String phone =jb.getString("phone");
        String code =jb.getString("code");
        String templateNo =jb.getString("templateNo");
		return sendMsgService.checkMsg(phone, code,templateNo);
	}
	@GetMapping("sendMSG.do")
	public ResponseInfo<Boolean>  sendMSG(@RequestParam String  recvObject,@RequestParam String sendType,String templateNo) {
		JSONObject json =new JSONObject();
		json.put("recvObject",recvObject);
		json.put("sendType",sendType);
		json.put("templateNo",templateNo);
		this.jmsMessagingTemplate.convertAndSend("InfoCenter_sendSMS", JSONObject.toJSONString(json));
        return ResponseInfo.success(true);
	}

	/**
	 * 同步发送短信
	 */
	@PostMapping("send/message")
	public ResponseInfo<String> sendMsg(@RequestBody String body){
		log.info("REST request to send message.body: {}", body);
		ResponseInfo<String> result = sendMsgService.sendMsg(body);
		log.info("REST request to send message. success! result: {}", result);
		return result;
	}
}

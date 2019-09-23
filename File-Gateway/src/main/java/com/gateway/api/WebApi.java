package com.gateway.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.gateway.service.FileService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jace
 *
 */
@RestController
@Slf4j
public class WebApi {


	
	@Autowired
	private FileService service;
	
	
	/**
	 *	上传
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/api/upload/base64")
	public JSONObject uploadBase64(@RequestBody JSONObject json,@RequestHeader("token")String token) throws Exception {
		JSONObject res = service.uploadBase64(json,token);
		log.info("uploadBase64 ==> {}",res);
		return res;
	}
	
	
	@PostMapping("/api/upload/file")
	public JSONObject uploadMultipartFile(@RequestPart("file")MultipartFile file,@RequestHeader("token")String token,int type) throws Exception {
		JSONObject json = service.uploadMultipartFile(file,token,type);
		log.info("uploadMultipartFile ==> {}",json);
		return json;
	}
	
}

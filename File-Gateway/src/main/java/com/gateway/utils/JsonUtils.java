package com.gateway.utils;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

	public static final String SUCCESS_CODE = "0000"; 
	
	public static final String ERROR_CODE = "9999"; 
	
	public static final String SUCCESS_MSG = "上传文件成功";
	
	public static final String ERROR_MSG = "上传文件失败";
	
	
	/**
	 *	交互成功
	 * @param data
	 * @return
	 */
	public static JSONObject success(JSONObject data) {
		
		return create(data, SUCCESS_CODE, SUCCESS_MSG);
	}
	
	
	
	/**
	 *  失败
	 * @param data
	 * @return
	 */
	public static JSONObject error(JSONObject data) {

		return create(data, ERROR_CODE, ERROR_MSG);
	}
	
	
	
	public static JSONObject create(JSONObject data,String code,String message) {
		JSONObject json = new JSONObject();
	    json.put("code", code);
	    json.put("message", message);
	    json.put("data", data);
		return json;
	}
}

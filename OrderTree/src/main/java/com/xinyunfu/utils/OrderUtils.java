package com.xinyunfu.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Administrator
 *
 * 树节点编号生成工具
 */
public class OrderUtils {

	public static String NODE_NO_PRE = "order";
	
	public static String PUSH_LIST_KEY = "PUSH_";
	
	
	public static String createNodeNo() {
		return new StringBuilder(NODE_NO_PRE).append(System.currentTimeMillis()+"").append(new Random().nextInt(1000)+"").toString();
	}
	
	
	
	/**
	 * 用户编号
	 * @param userNo
	 * @return
	 */
	public static String createPushlistKey(String userNo) {
		return PUSH_LIST_KEY.concat(userNo);
	}
	
	
	
}

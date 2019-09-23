package com.xinyunfu.jace.utils;

import java.util.UUID;

import org.springframework.util.StringUtils;

public class ThreadUtils {
	
	
	private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
	
	
	
	public static String getThreadId() {
		String id = threadLocal.get();
		if(StringUtils.isEmpty(id)) {
			id = UUID.randomUUID().toString();
			Thread.currentThread().setName(id);
			threadLocal.set(id);
		}
		return id;
	}

}

package com.xinyunfu.utils;


import java.util.HashMap;
import java.util.Map;

import com.xinyunfu.model.AppUser;


/**
 * @author liuzhen
 *  线程变量公共类
 */
public class ThreadLocalUtil {
	/**
	 * 线程变量
	 */
    public static final ThreadLocal<Map<String, Object>> session = new ThreadLocal<Map<String, Object>>();
    
    private static Map<String, Object> map =null;
    /**
     * 放入set值
     * @param key
     * @param value
     */
    public static void set(String key,Object value){
    	getMap().put(key, value);
    }
    /**
     * 取值
     * @param key
     * @return
     */
    public static Object get(String key){
    	return getMap().get(key);
    }
    /**
     * 保存用户信息
     * @param userInfo
     */
    public static void setUserInfo(AppUser userInfo){
    	getMap().put(SysConstantsEnum.currentUse,userInfo);
    }
    /**
     * 取用户信息
     * @return
     */
    public static AppUser getUserInfo(){
    	return  getMap().get(SysConstantsEnum.currentUse) ==null ? null:(AppUser)session.get().get(SysConstantsEnum.currentUse);
    }    
    public static Map<String, Object> getMap(){
    	if(session.get() ==null) {
    		map =new HashMap<>();
    		session.set(map);
    	}
    	return session.get();
    }
}


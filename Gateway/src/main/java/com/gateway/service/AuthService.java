package com.gateway.service;

import java.io.IOException;
import java.nio.charset.Charset;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.gateway.utils.BusinessException;
import com.gateway.utils.JwtUtils;
import com.gateway.utils.MD5Utils;
import com.gateway.utils.RSAEncrypt;
import com.gateway.utils.ResponseInfo;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

	@Value("${token.white.list}")
	private String listString;
	
	@Value("${handler.req.url}")
	private String requestUrls;
	
	@Value("${handler.encry.key}")
	private String encryKey;
	
	@Value("${handler.req.time}")
	private String reqTime;

	@Autowired
	private RedisService redis;
	
	@Value("${handler.auth.md5.key}")
	private String md5Key;
	
	@Value("${auth.isOn.takeOn}")
    private String isOn;	
	
	@Value("${xyf.api.version}")
	private String apiVersion;
	
	/**
	 *	前置过滤逻辑
	 * @return
	 */
	public Object preFilter(RequestContext context) {
	    HttpServletRequest request = context.getRequest();
	    context.getResponse().setCharacterEncoding("UTF-8");
        context.addZuulResponseHeader("Content-Type", "application/json;charset=UTF-8");
        String url = request.getRequestURI();
        String token = request.getHeader("token");
        log.info("==========================={}==========================",url);
        try {
        	if(containUrl(url)&&checkRequest(url, token))throw new Exception("1:对不起您的操作太频繁");
        	if(!inWhitelist(url)) {  //  不在免验签白名单
        		String userNo = JwtUtils.getUserNoByToken(token);
        		log.info("==>  不在免登录名单 :{}",userNo);
        		checkToken(token,userNo);   //  验证不通过会直接报错throw exception
        		context.addZuulRequestHeader("currentUserId", userNo);
        		context.addZuulRequestHeader("X-Real-IP",  request.getHeader("X-Real-IP"));
        	}
            if("post".equalsIgnoreCase(request.getMethod())) {  // post请求才会有body
            	    ServletInputStream sis = request.getInputStream();
            	    String body = StreamUtils.copyToString(sis, Charset.forName("UTF-8"));
            		log.info("request body:{}",body);
            		JSONObject json = JSONObject.parseObject(body);
            		String data = json.getString("data");  //   业务加密报文
            		String sign = json.getString("sign");  //   签名报文
            		String obj = requestDecry(data);   //   解密业务报文
            		log.info("==>解密用户报文   requestBody: {} ",obj);
            		if(null==obj||obj.isEmpty()) {         //   业务报文为null
            			throw new Exception("缺少必填参数");
            		}
            		if(!signCheck(sign, obj)) {
            			throw new Exception("签名验证不通过");	 
            		}
            		if(!checkVersion(obj)) {
            			throw new Exception("APP版本无法兼容，请及时更新");
            		}
            		reSetRequestBody(context,JSONObject.parseObject(obj), request);//重置 requestBody
             }
		}catch (Exception e) {
			log.info("e message:==={}",e.getMessage());
			exceptionHandler(context, e);
		}
		return null;
	}

	
	


	/**
	 *	检查当前版本客户端版本是否兼容
	 * @param obj
	 * @return
	 */
	private boolean checkVersion(String obj) {
		JSONObject json = JSONObject.parseObject(obj);
		String clientVersion = json.getString("clientSystem");
		return RSAEncrypt.checkVersion(apiVersion, clientVersion);
	}





	/**
	 *	 返回的处理过滤器逻辑
	 * @return
	 * @throws IOException 
	 */
	public Object postFilter(RequestContext context)  {
		context.getResponse().setCharacterEncoding("UTF-8");
		context.addZuulResponseHeader("Content-Type", "application/json;charset=UTF-8");
		String body;
		try {
			body = StreamUtils.copyToString(context.getResponseDataStream(), Charset.forName("UTF-8"));
			log.info("==== 返回内容  ==>{}",body);
	        JSONObject json = JSONObject.parseObject(body);
	        String sign = MD5Utils.md5(new StringBuilder(MD5Utils.md5(md5Key)).append(body).toString());
	        json.put("sign", sign);
	        json.put("apiVersion", apiVersion);
	        log.info("post filter body:"+json.toJSONString());
	        context.setResponseBody(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject json = new JSONObject();
			json.put("code", ResponseInfo.CODE_ERROR);
			json.put("data", e.getMessage());
			json.put("message", e.getMessage());
			json.put("apiVersion", apiVersion);
			context.setResponseBody(json.toJSONString());
		}
        return null;
	}
	
	
	/**
	 * 	报文解密请求解密
	 * @param str
	 * @return
	 */
	public String requestDecry(String str) throws Exception{
		String decryStr = RSAEncrypt.decryptString(str, encryKey);
		
		return decryStr;
	}

	/**
	 *	签名验证
	 * @param body
	 * @param sign
	 * @param userNo
	 * @return
	 * @throws Exception 
	 */
	public boolean signCheck(String sign,String jsonString) throws Exception {
		
		String str = MD5Utils.md5(new StringBuilder(MD5Utils.md5(md5Key)).append(jsonString).toString());
		log.info("验证签名（内部生成MD5）："+str);
		return sign!=null && sign.equals(str);
	}


	
	
	/**
	 * 	是否在白名单检查
	 * @param url
	 * @return
	 */
	public boolean inWhitelist(String url) {
	    String [] ss = listString.split(","); 
	    for(String s : ss) {
	    	if(url.startsWith(s)) {
	    		return true;
	    	}
	    }
		return false;
	}
	
	

	/**
	 * token有效性验证
	 * @param token
	 * @return
	 */
	public boolean checkToken(String token,String userNo) throws BusinessException{
		log.info("zuulfilter get token:"+token);
		log.info("encryVersion:"+apiVersion);
        if(StringUtils.isEmpty(token)) {
        	throw new BusinessException("您还没有登录，请登录再操作");
        }
        
        if(!JwtUtils.checkToken(token, apiVersion)) {
        	throw new BusinessException("操作不受信任，请重新登录");
        }
        
        if(!token.equals(redis.get("gateway"+userNo))) {
        	throw new BusinessException("您已经在其他地方登录了，如要操作请重新登录当前设备");
        }
		return true;
	}
	
	
	/**
	 *	重新设置访问的requestBody
	 * @param rc
	 * @param obj
	 * @param request
	 */
	public void reSetRequestBody(RequestContext rc,JSONObject obj,HttpServletRequest request) {
		byte[] bytes = obj.toJSONString().getBytes();
		rc.setRequest(new HttpServletRequestWrapper(request) {
            @Override
            public ServletInputStream getInputStream() throws IOException {
                return new ServletInputStreamWrapper(bytes);
            }
            @Override
            public int getContentLength() {
                return bytes.length;
            }
            @Override
            public long getContentLengthLong() {
                return bytes.length;
            }
        });
	}
	
	
	/**
	 *	判断是否需要进行频控
	 * @param url
	 * @return
	 */
	public boolean containUrl(String url) {
		String[] urls = requestUrls.split(",");
        for(String u : urls) {
        	if(u.equals(url))return true;
        }		
        return false;
	}
	
	
	/**
	 * @param context
	 * @param e 不是businessException的对象
	 */
	public void exceptionHandler(RequestContext context , Exception e) {
		JSONObject json = new JSONObject();
		String code = ResponseInfo.CODE_ERROR;
		String message = "";
		if(e instanceof BusinessException) {
			code = "00001";
			message = e.getMessage();
		}else {
			if(e.getMessage().startsWith("APP")) {
				code = "00002";
				message = e.getMessage();
			}
			if(e.getMessage().startsWith("1:")) {
				code = "00003";
				message = "对不起您的操作太频繁";
			}
		}
		json.put("code", code);
		json.put("data", message);
		json.put("message", message);
		json.put("apiVersion", apiVersion);
		context.setSendZuulResponse(false);// 设置不转发
    	context.setResponseStatusCode(200);
    	context.setResponseBody(json.toJSONString());
    	context.set("isSuccess", false);  
    	
	}
	
	
	/**
	 * 	重复提交判断
	 * @param url
	 * @param token
	 * @return
	 */
	public boolean checkRequest(String url,String token) {
		String requestKey = MD5Utils.md5(token.concat(token));
		if(redis.existsKey(requestKey)) {
			return true;
		}else {
            redis.set(requestKey, url, 3000);
			return false;
		}
	}
	
}

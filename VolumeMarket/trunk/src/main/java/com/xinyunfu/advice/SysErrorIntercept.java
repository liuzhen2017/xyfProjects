package com.xinyunfu.advice;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyunfu.jace.utils.ResponseInfo;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class SysErrorIntercept {
	
	
	 /**
              *  全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseInfo<String> errorHandler(HttpServletRequest request,Exception ex) {
    	
    	log.error("=============访问{}发生异常，异常信息={},堆栈={}===============",request.getRequestURI(),ex.getMessage(),ex);
        return ResponseInfo.errorReturn("系统异常!");
    }
 
  
}

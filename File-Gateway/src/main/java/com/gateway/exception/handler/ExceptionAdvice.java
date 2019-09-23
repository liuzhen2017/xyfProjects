package com.gateway.exception.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alibaba.fastjson.JSONObject;
import com.gateway.exception.BusinessException;
import com.gateway.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice{

	
  
    /**
     *
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public JSONObject defaultErrorHandler(Exception ex) throws Exception {
        log.error("", ex);
        JSONObject result = new JSONObject();
        if(ex instanceof BusinessException) {
        	result.put("data", ex.getMessage());
        }
        return JsonUtils.error(result);
    }
}

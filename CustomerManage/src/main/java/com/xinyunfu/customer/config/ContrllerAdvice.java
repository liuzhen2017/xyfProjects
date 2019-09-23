package com.xinyunfu.customer.config;

import com.rnmg.jace.utils.ResponseInfo;
import com.xinyunfu.customer.constant.JSR303Constant;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.service.log.CustomerLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice
public class ContrllerAdvice implements ResponseBodyAdvice<Object> {

    @Autowired private CustomerLogService customerLogService;
    /**
     * 异常拦截
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public ResponseInfo errorHandler(Exception e){

        ResponseInfo responseInfo = ResponseInfo.error(null);

        if(e instanceof CustomerException){
            CustomerException ce = (CustomerException) e;
            responseInfo.setCode(ce.getErrCode() + "");
            responseInfo.setMessage(ce.getDesc());
            customerLogService.saveLogWithException(ce.getExceptionEnum());
        }else if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException me = (MethodArgumentNotValidException) e;
            responseInfo.setCode(JSR303Constant.JSR_ERROR_CODE);
            responseInfo.setMessage(me.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        }
        log.error("error : {}", e);

        return responseInfo;
    }

    /**
     * 判断是否需要处理返回值
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 统一处理返回值
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof ResponseInfo) return body;
        return ResponseInfo.success(body);
    }
}

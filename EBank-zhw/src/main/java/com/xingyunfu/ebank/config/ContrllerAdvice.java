package com.xingyunfu.ebank.config;

import com.rnmg.jace.utils.ResponseInfo;
import com.xingyunfu.ebank.constant.PayCenterConstant;
import com.xingyunfu.ebank.exception.EBankException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice
public class ContrllerAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 异常拦截
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public ResponseInfo errorHandler(Exception e){

        ResponseInfo responseInfo = ResponseInfo.error(null);

        if(e instanceof EBankException){
            EBankException ce = (EBankException) e;
            responseInfo.setCode(ce.getErrCode() + "");
            responseInfo.setMessage(ce.getDesc());
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
        //支付中心回调结果不处理
        if(PayCenterConstant.callback_success.equals(body)) return body;
        if(body instanceof ResponseInfo) return body;
        return ResponseInfo.success(body);
    }
}

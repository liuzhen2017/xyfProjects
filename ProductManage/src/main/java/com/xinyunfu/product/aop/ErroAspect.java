package com.xinyunfu.product.aop;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.utils.ResInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class ErroAspect {

    @Pointcut("execution(* com.xinyunfu.product.controller.*.*(..))")
    public void performance() {}

    @Around("performance()")
    public Object watchPerformance(ProceedingJoinPoint joinPoint) {
        Object res =null;
        try {
            res = joinPoint.proceed();
        } catch (Throwable e) {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            //获取请求
            String url = request.getRequestURL().toString();
            //获取方法
            String method = joinPoint.getSignature().getName();
            //获取参数
            String params = StringUtils.join(joinPoint.getArgs(), ",");
            log.error("[全局异常]==========> url: {}, method: {}, params: {}, message: {}", url, method, params,e.getMessage());
            e.printStackTrace();
            if(e.getMessage().contains("Exception")) {
            	return ResInfo.errorReturn("系统繁忙,请稍后!");
            }else {
            	return new ResInfo().disposeReturn(e.getMessage());
            }
            
        }
        return res;
    }
}

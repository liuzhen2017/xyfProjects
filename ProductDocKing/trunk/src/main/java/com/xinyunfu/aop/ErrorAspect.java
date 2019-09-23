package com.xinyunfu.aop;


import com.xinyunfu.jace.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XRZ
 * @date 2019/7/8 0008
 * @Description : 用于捕获记录全局异常
 */
@Slf4j
@Aspect
@Component
public class ErrorAspect {

    @Pointcut("execution(* com.xinyunfu.*.*(..))")
    public void performance() {}

    @Around("performance()")
    public Object watchPerformance(ProceedingJoinPoint joinPoint) {
        Object res = null;
        try {
            res = joinPoint.proceed();
        } catch (Throwable e) {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            //获取请求
            String url = request.getRequestURL().toString();
            //获取方法
            String method = request.getMethod();
            //获取参数
            String queryString = request.getQueryString();
            log.error("[全局异常]==========> url: {}, method: {}, params: {}, message: {}", url, method, queryString,e.getMessage());
            return ResponseInfo.error(null);
        }
        return res;
    }
}

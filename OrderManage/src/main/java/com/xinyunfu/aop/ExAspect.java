package com.xinyunfu.aop;


import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.IErrorInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XRZ
 * @date 2019/7/8 0008
 * @Description : 用于捕获记录全局异常
 */
@Slf4j
@Aspect
@Component
public class ExAspect {

    @Autowired
    private IErrorInfoService iErrorInfoService;

    @Pointcut("execution(* com.xinyunfu.web..*.*(..))")
    public void performance() {}


    @Around("performance()")
    public Object watchPerformance(ProceedingJoinPoint joinPoint) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String url = request.getRequestURL().toString();
        String method = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Map<String, Object> params = this.getParams(joinPoint.getSignature(), joinPoint.getArgs());
        log.info("========================================== Start ==========================================");
        log.info("URL            : {}", url);
        log.info("HTTP Method    : {}", request.getMethod());
        log.info("Class Method   : {}", method);
        log.info("IP             : {}", request.getRemoteAddr());
        log.info("Request Args   : {}", params);
        long startTime = System.currentTimeMillis();
        Object res = null;
        try {
            res = joinPoint.proceed();
        } catch (Throwable e) {
            StackTraceElement element = e.getStackTrace()[0];
            //返回指定格式
            Map<String,Object> map = new HashMap<>();
            map.put("records",new int[]{});
            if(e instanceof CustomException){
                CustomException ce = (CustomException) e;
                ExecptionEnum ee = ce.getEe();
                log.error("【全局异常】====================> url ： {}, method ： {}, params ： {}, code ： {}, message ： {}, fileName : {}, errorLineNumber ： {}, isOther ： {}", url, method, params,ee.getCode(),ee.getMessage(),element.getFileName(),element.getLineNumber(),ce.isOther());
                if(ee.isLog()){ //如果需要记录日志
                    iErrorInfoService.recordErrorInfo(request.getMethod(),joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName(),params,ee.getCode(),ee.getMessage());
                }
                if(ee.isShow()) //如果展示给前端看
                    return new ResponseInfo<>(ee.getCode()+"",ee.getMessage(),map);
                return new ResponseInfo<>("9999",ee.getMessage(),map);
            }
            log.error("【全局异常】====================> url ： {}, method ： {}, params ： {}, message ： {}, fileName : {},errorLineNumber ： {}", url, method, params,e.getMessage(),element.getFileName(),element.getLineNumber());
            e.printStackTrace();
            return new ResponseInfo<>("9999",e.getMessage(),map);
        }
        log.info("Response Args  : {}", res);
        log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
        log.info("=========================================== End ===========================================");
        log.info("");
        return res;
    }

    /**
     * 获取参数名即对应值
     * @param signature
     * @param values
     * @return
     */
    public Map<String,Object> getParams(Signature signature, Object[] values){
        Map<String,Object> map = new HashMap<>();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        for (int i = 0; i < values.length; i++) {
            map.put(parameterNames[i],values[i]);
        }
        return map;
    }




}

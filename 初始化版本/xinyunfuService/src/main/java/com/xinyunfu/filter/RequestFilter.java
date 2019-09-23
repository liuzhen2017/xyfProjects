package com.xinyunfu.filter;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.model.AppUser;
import com.xinyunfu.utils.SysConstantsEnum;
import com.xinyunfu.utils.ThreadLocalUtil;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Map;

@Slf4j
@Configuration
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("==============begin request,requestUrl={},parmat={}",request.getRequestURL(),JSONObject.toJSONString(parameterMap));
        try {
            String userNo = request.getHeader(SysConstantsEnum.currentUse);
            if(StringUtils.isNotEmpty(userNo) ){
            	//TODO 等待用户管理微服务提供用户 查询接口
                AppUser userInfo =null;//查询微服务,
                
                //TODO 模拟用户信息
                userInfo =new AppUser();
                userInfo.getId();
                
                ThreadLocalUtil.setUserInfo(userInfo);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            try {
                sendMessage(response, JSONObject.toJSONString(request.getAttribute("message")));
            } catch (Exception ex) {
                log.error(ExceptionUtils.getFullStackTrace(e));
            }
        }
    }

    @Override
    public void destroy() {

    }

    public void sendMessage(HttpServletResponse response, String str) throws Exception {
        log.info("=========  end doFilter, result = {}  =====", str);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(str);
        writer.close();
        response.flushBuffer();
    }
}

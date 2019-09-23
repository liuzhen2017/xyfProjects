package com.xinyunfu.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author XRZ
 * @date 2019/8/5
 * @Description :
 */

@Component
@WebFilter(urlPatterns = "/*", filterName = "authFilter")
public class PassHttpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 跨域处理
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
        httpResponse.setHeader("Access-Control-Allow-Headers","*");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        filterChain.doFilter(servletRequest, httpResponse);
    }

    @Override
    public void destroy() {
    }
}

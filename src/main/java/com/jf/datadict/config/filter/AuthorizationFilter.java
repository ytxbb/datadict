package com.jf.datadict.config.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器：校验接口访问权限
 */
public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse rsp = (HttpServletResponse) servletResponse;
        if (!req.getRequestURI().startsWith("/static") && !req.getRequestURI().startsWith("/favicon.ico")
                && !req.getServletPath().equals("/costomShow")
                && !req.getServletPath().equals("/validateMySql")
                && !req.getServletPath().equals("/")) {
            if (req.getSession().getAttribute("url")==null) {
                rsp.sendRedirect((req.getContextPath()+"/"));
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
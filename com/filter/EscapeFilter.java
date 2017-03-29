package com.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by bigwh on 2017/3/23.
 */
@WebFilter(filterName = "EscapeFilter",urlPatterns = "/*")
public class EscapeFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request=new com.controller.EscapeWrapper((HttpServletRequest) req);
        chain.doFilter(request, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

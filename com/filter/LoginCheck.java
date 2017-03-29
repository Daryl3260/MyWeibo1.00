package com.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bigwh on 2017/3/22.
 */
@WebFilter(filterName = "LoginCheck", urlPatterns = {"/login.view","/message.do"})
public class LoginCheck implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest)req;
        HttpServletResponse response=(HttpServletResponse)resp;
        String index=request.getServletContext().getInitParameter("index");
        String username=(String)request.getSession().getAttribute("login");
        if(username==null)request.getRequestDispatcher(index).forward(request,response);
        else chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

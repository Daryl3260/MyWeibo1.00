package com.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.controller.SQLService;
import com.controller.Service;
import com.controller.UserService;

/**
 * Created by bigwh on 2017/3/23.
 */
public class UserServiceListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context=sce.getServletContext();
        //Service service=new UserService(context.getInitParameter("workplace"));
        Service service=new SQLService(context);
        context.setAttribute("userService",service);
    }
}

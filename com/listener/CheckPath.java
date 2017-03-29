package com.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * Created by bigwh on 2017/3/22.
 */
public class CheckPath implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String workplace=sce.getServletContext().getInitParameter("workplace");
        File file=new File(workplace);
        if(!file.exists()){
            file.mkdir();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

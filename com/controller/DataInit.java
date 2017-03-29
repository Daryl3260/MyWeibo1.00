package com.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;
import java.util.HashMap;

/**
 * Created by bigwh on 2017/2/14.
 */
public class DataInit implements ServletContextListener {//listener must be registered in web.xml
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        File file=new File("data.dat");
        System.out.println(file.getAbsolutePath());
        if(!file.exists()){
            try{
                file.createNewFile();
                HashMap<String,String> map=new HashMap<>();
                ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(map);
            }
            catch (IOException e){e.printStackTrace();}
        }
    }
}

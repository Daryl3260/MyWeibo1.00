package com.controller;

import com.javabean.Blah;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bigwh on 2017/2/14.
 */
@WebServlet(name = "Login")
public class Login extends HttpServlet {
    protected void process(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String success=getServletContext().getInitParameter("login_success");
        String fail=getServletContext().getInitParameter("login_fail");
        String index=getServletContext().getInitParameter("index");
        //System.out.println(request.getParameter("autoLogin"));
        if(username==null||password==null){
            //response.sendRedirect(index);
            request.getRequestDispatcher(index).forward(request,response);
        }else{
            Service service=(Service)request.getServletContext().getAttribute("userService");
            if(service.checkLogin(username,password)){
                request.getSession().setAttribute("login",username);
                String autoLogin=request.getParameter("autoLogin");
                if("on".equals(autoLogin)){
                    request.getSession().setAttribute("username",username);
                    request.getSession().setAttribute("password",password);
                }else{
                    request.getSession().removeAttribute("username");
                    request.getSession().removeAttribute("password");
                }
                List<Blah> blahs=service.readMessage(username);
                request.setAttribute("blahs",blahs);
                request.getRequestDispatcher(success).forward(request,response);
                return;
            }else
                request.setAttribute("error","Wrong username or password");
                request.getRequestDispatcher(index).forward(request,response);
                return;
                //response.sendRedirect(fail);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }
}

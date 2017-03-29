package com.controller;

import com.javabean.Blah;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by bigwh on 2017/3/24.
 */
@WebServlet(name = "User",urlPatterns = "/user/*")
public class User extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Service service=(Service) request.getServletContext().getAttribute("userService");
        //System.out.println(request.getPathInfo());
        String browse=request.getServletContext().getInitParameter("browse");
        String username=request.getPathInfo().substring(1);//skip the first / character
        request.setAttribute("username",username);
        if(service.invalidUsername(username)){
            List<Blah> blahs=service.readMessage(username);
            request.setAttribute("blahs",blahs);
        }
        request.getRequestDispatcher(browse).forward(request,response);
    }
}

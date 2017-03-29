package com.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedWriter;
import java.io.*;
import java.util.HashMap;

/**
 * Created by bigwh on 2017/2/14.
 */
@WebServlet(name = "CreateAccount")
public class CreateAccount extends HttpServlet {
    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String register=getServletContext().getInitParameter("register");
        String index=getServletContext().getInitParameter("index");
        String email=request.getParameter("email");
        //String workplace=getServletContext().getInitParameter("workplace");
        //File user=new File(workplace+"/"+username);
        Service service=(Service)request.getServletContext().getAttribute("userService");
        if(service.invalidUsername(username)){
            request.setAttribute("error","Username Taken");
            request.getRequestDispatcher(register).forward(request,response);
        }
        else{
            service.createAccount(username,password,email);
            request.setAttribute("message","Registered");
            request.getRequestDispatcher(index).forward(request,response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        process(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }
}

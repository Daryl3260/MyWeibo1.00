package com.controller;

import com.javabean.Blah;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by bigwh on 2017/3/22.
 */
@WebServlet(name = "Message", urlPatterns = "/message.do")
public class Message extends HttpServlet {
    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String blabla=request.getParameter("blabla");
        if(blabla.length()>140){
            response.sendRedirect(request.getServletContext().getInitParameter("login_success"));
            //return;
        }else{
            String username=(String)request.getSession().getAttribute("login");
            String workplace=request.getServletContext().getInitParameter("workplace");
            Service service=(Service)request.getServletContext().getAttribute("userService");
            Blah blah=new Blah();
            blah.setUsername(username);
            blah.setTxt(blabla);
            blah.setDate(new Date());
            service.addMessage(blah);
            //response.sendRedirect(request.getServletContext().getInitParameter("login_success"));
            List<Blah> blahs=service.readMessage(username);
            request.setAttribute("blahs",blahs);
            request.getRequestDispatcher(request.getServletContext().getInitParameter("login_success")).forward(request,response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }
}

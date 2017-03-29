package com.controller;

import com.javabean.Blah;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by bigwh on 2017/3/22.
 */
@WebServlet(name = "Delete",urlPatterns = "/delete.do")
public class Delete extends HttpServlet {
    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Service service=(Service)request.getServletContext().getAttribute("userService");
        Blah blah=new Blah();
        blah.setUsername((String)request.getSession().getAttribute("login"));
        blah.setId(Integer.parseInt(request.getParameter("id")));
        blah.setDate(new Date(Long.parseLong(request.getParameter("time"))));
        service.deleteMessage(blah);
        List<Blah> blahs=service.readMessage((String)request.getSession().getAttribute("login"));
        request.setAttribute("blahs",blahs);
        //request.getRequestDispatcher(success).forward(request,response);
        //response.sendRedirect(request.getServletContext().getInitParameter("login_success"));
        request.getRequestDispatcher(request.getServletContext().getInitParameter("login_success")).forward(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }
}

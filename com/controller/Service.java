package com.controller;

import com.javabean.Blah;

import java.io.IOException;
import java.util.List;

/**
 * Created by bigwh on 2017/3/27.
 */
public interface Service {
    boolean invalidUsername(String username);
    void createAccount(String username,String password,String email) throws IOException;
    boolean checkLogin(String username,String password) throws IOException;
    void addMessage(Blah blah) throws IOException;
    void deleteMessage(Blah blah);
    List<Blah> readMessage(String user) throws IOException;
}

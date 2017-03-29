package com.javabean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bigwh on 2017/3/24.
 */
public class Blah implements Serializable{
    private String username;
    private Date date;
    private String txt;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

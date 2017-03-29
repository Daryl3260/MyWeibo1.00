package com.controller;

import com.javabean.Blah;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by bigwh on 2017/3/27.
 */
public class SQLService implements Service {
    private String driver;
    private String jdbcUrl;
    private String username;
    private String password;
    public SQLService(ServletContext context){
        driver=context.getInitParameter("driver");
        jdbcUrl=context.getInitParameter("jdbcUrl");
        username=context.getInitParameter("SQLUsername");
        password=context.getInitParameter("SQLPassword");
        try{Class.forName(driver);}
        catch (ClassNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean invalidUsername(String username) {
        List<String> usernames=new ArrayList<>();
        SQLException e=null;
        Connection connection=null;
        try{
            connection= DriverManager.getConnection(this.jdbcUrl,this.username,this.password);
            PreparedStatement statement=connection.prepareStatement("SELECT username FROM member");
            ResultSet set=statement.executeQuery();
            while(set.next())usernames.add(set.getString(1));
        }catch (SQLException ex){
            e=ex;
        }
        try{
            if(connection!=null)connection.close();
        }catch (SQLException ex){
            if(e==null)e=ex;
        }
        if(e!=null)throw new RuntimeException(e);
        return usernames.contains(username);
    }

    @Override
    public void createAccount(String username, String password,String email) throws IOException {
        //SQLException e=null;
        Connection connection=null;
        try{
            connection= DriverManager.getConnection(this.jdbcUrl,this.username,this.password);
            PreparedStatement statement=connection.prepareStatement("INSERT INTO  member(username,email,passwd)VALUES(?,?,?)");
            statement.setString(1,username);
            statement.setString(2,email);
            statement.setString(3,password);
            statement.executeUpdate();
            statement=connection.prepareStatement("CREATE TABLE "+username+
                    "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "w_date DATE NOT NULL," +
                    "w_time TIME NOT NULL," +
                    "txt TEXT NOT NULL)");
            //statement.setString(1,username);
            statement.executeUpdate();
            connection.close();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean checkLogin(String username, String password) throws IOException {
        boolean r;
        HashMap<String,String> map=new HashMap<>();
        try{
            Connection connection=DriverManager.getConnection(this.jdbcUrl,this.username,this.password);
            PreparedStatement statement=connection.prepareStatement("SELECT username,passwd FROM member");
            ResultSet set=statement.executeQuery();
            while(set.next()){
                String _username=set.getString(1);
                String _passwd=set.getString(2);
                map.put(_username,_passwd);
            }
            if(!map.keySet().contains(username)) r=false;
            else r=map.get(username).equals(password);
            connection.close();
        }catch (SQLException ex){throw new RuntimeException(ex);}
        return r;
    }

    @Override
    public void addMessage(Blah blah) throws IOException {
        String username=blah.getUsername();
        String txt=blah.getTxt();
        Date date=new Date(blah.getDate().getTime());
        Time time=new Time(blah.getDate().getTime());
        try{
            Connection connection=DriverManager.getConnection(this.jdbcUrl,this.username,this.password);
            PreparedStatement statement=connection.prepareStatement("INSERT INTO "+username+"(w_date,w_time,txt)VALUES (?,?,?)");
            //statement.setString(1,username);
            statement.setDate(1,date);
            statement.setTime(2,time);
            statement.setString(3,txt);
            statement.executeUpdate();
            connection.close();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Blah> readMessage(String user) throws IOException {
        List<Blah> blahs=new ArrayList<>();
        try{
            Connection connection=DriverManager.getConnection(this.jdbcUrl,this.username,this.password);
            PreparedStatement statement=connection.prepareStatement("SELECT * FROM "+user,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //statement.setString(1,user);
            ResultSet resultSet=statement.executeQuery();
            resultSet.afterLast();
            while(resultSet.previous()){
                Blah blah=new Blah();
                blah.setUsername(user);
                blah.setTxt(resultSet.getString(4));
                Date date=resultSet.getDate(2);
                Time time=resultSet.getTime(3);
                blah.setDate(new java.util.Date(date.getTime()+time.getTime()));
                blah.setId(resultSet.getInt(1));
                //blah.setDate(date);
                //blah.setDate(time);
                blahs.add(blah);
            }
            connection.close();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return blahs;
    }

    @Override
    public void deleteMessage(Blah blah) {
        String username=blah.getUsername();
        int id=blah.getId();
        try{
            Connection connection=DriverManager.getConnection(this.jdbcUrl,this.username,this.password);
            PreparedStatement statement=connection.prepareStatement("DELETE FROM "+username+" where id=?");
            //statement.setString(1,username);
            statement.setInt(1,id);
            statement.executeUpdate();
            connection.close();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }
}

package com.sql;

import java.io.Serializable;
import java.sql.*;
/**
 * Created by bigwh on 2017/3/25.
 */
public class DbBean implements Serializable {
    private String jdbcUrl;
    private String username;
    private String password;
    public DbBean(){
        try{Class.forName("com.mysql.jdbc.Driver");}
        catch (ClassNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }
    public boolean isConnectedOK(){
        boolean ok=false;
        Connection conn=null;
        SQLException ex=null;
        try{
            conn=DriverManager.getConnection(jdbcUrl,username,password);
            if(!conn.isClosed())ok=true;
        }catch (SQLException e){ex=e;}finally {
            if(conn!=null){
                //System.out.println("Connected");
                try{
                    conn.close();
                }catch (SQLException e){
                    if(ex==null)ex=e;
                }
            }
            if(ex!=null)throw new RuntimeException(ex);
        }
        return ok;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

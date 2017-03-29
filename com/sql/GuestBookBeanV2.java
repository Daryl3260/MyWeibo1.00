package com.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigwh on 2017/3/26.
 */
public class GuestBookBeanV2 {
    private String jdbcUrl="jdbc:mysql://localhost:3306/demo";
    private String username="root";
    private String password="water930731";
    public GuestBookBeanV2(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }
    public void setMessage(Message message){
        Connection conn=null;
        PreparedStatement statement=null;
        SQLException ex=null;
        try{
            conn=DriverManager.getConnection(jdbcUrl,username,password);
            statement=conn.prepareStatement("INSERT INTO t_message VALUES(name,email,msg) (?,?,?)");
            statement.setString(1,"Daryl");
            statement.setString(2,"cbhnk@sina.cn");
            statement.setString(3,"PreparedStatement is much more convenient");
            statement.executeUpdate();
            statement.clearParameters();
        }catch (SQLException e){
            ex=e;
        }
        if(conn!=null)
        try{
            conn.close();
        }catch (SQLException e){
            if(ex==null)ex=e;
        }
        if(ex!=null)throw new RuntimeException(ex);
    }
    public List<Message> getMessages(){
        Connection conn=null;
        PreparedStatement statement=null;
        SQLException ex=null;
        List<Message> messages=null;
        ResultSet rs=null;
        try{
            conn=DriverManager.getConnection(jdbcUrl,username,password);
            statement=conn.prepareStatement("SELECT * FROM t_message");
            rs=statement.executeQuery();
            messages=new ArrayList<>();
            while(rs.next()){
                Message message=new Message();
                message.setId(rs.getLong(1));
                message.setName(rs.getString(2));
                message.setEmail(rs.getString(3));
                message.setMsg(rs.getString(4));
                messages.add(message);
            }
        }catch (SQLException e){
            ex=e;
        }
        if(conn!=null)
            try{conn.close();}catch (SQLException e){
            if(ex!=null)ex=e;
            }
        if(ex!=null)throw new RuntimeException(ex);
        return messages;
    }
}

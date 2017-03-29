package com.controller;

import com.javabean.Blah;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by bigwh on 2017/3/23.
 */
public class UserService implements Service {
    private String workplace;
    public UserService(String workplace){
        this.workplace=workplace;
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }
    public boolean invalidUsername(String username){
        String[] UserDir=new File(workplace).list();
        if(UserDir==null)return false;
        for(String user:UserDir){
            if(user.equals(username))return true;
        }
        return false;
    }
    public void createAccount(String username,String password,String email)throws IOException{
        File user=new File(workplace+"/"+username);
        user.mkdir();
        File data=new File(workplace+"/"+username+"/"+"data.dat");
        FileWriter writer=new FileWriter(data);
        writer.write(password+"\n");
        writer.flush();
        writer.close();
    }
    public boolean checkLogin(String username,String password)throws IOException{
        File user=new File(workplace+"/"+username);
        if(!user.exists())return false;
        File data=new File(workplace+"/"+username+"/"+"data.dat");
        BufferedReader reader=new BufferedReader(new FileReader(data));
        String psw=reader.readLine();
        reader.close();
        return psw.equals(password);
    }
    public void addMessage(Blah blah)throws IOException{
        String username=blah.getUsername();
        String blabla=blah.getTxt();
        File txt=new File(workplace+"/"+username+"/"+new Date().getTime()+".txt");
        BufferedWriter writer=new BufferedWriter(new FileWriter(txt));
        writer.write(blabla);
        writer.close();
    }
    public void deleteMessage(Blah blah){
        String username=blah.getUsername();
        Long time=blah.getDate().getTime();
        File txt=new File(workplace+"/"+username+"/"+time+".txt");
        if(txt.exists())txt.delete();
    }
    public List<Blah> readMessage(String user)throws IOException{
        File userDir=new File(workplace+"/"+user);
        String[] txts=userDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(".txt");
            }
        });
        List<Blah> blahs=new ArrayList<>();
        if(txts!=null)
        for(String txt:txts){
            StringBuffer sb=new StringBuffer();
            BufferedReader reader=new BufferedReader(new FileReader(workplace+"/"+user+"/"+txt));
            String oneLine=null;
            while((oneLine=reader.readLine())!=null)sb.append(oneLine);
            reader.close();;
            Blah blah=new Blah();
            blah.setUsername(user);
            blah.setDate(new Date(Long.parseLong(txt.substring(0,txt.indexOf(".txt")))));
            blah.setTxt(sb.toString());
            blahs.add(blah);
        }
        blahs.sort((b0,b1)->{
            Date d0=b0.getDate();
            Date d1=b1.getDate();
            return -d0.compareTo(d1);
        });
        return blahs;
    }
}

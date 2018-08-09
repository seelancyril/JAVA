package com.fileloader.extractor.test;

import com.fileloader.extractor.utils.DBConnection;

import java.sql.Connection;

public class TestConnection {

    public Connection testConnection(){
        try{
            DBConnection dbcon = new DBConnection();
            Connection con = dbcon.getConnection();
            if(con.isValid(0))
                System.out.println("Connection successful: "+ con);
            else
                System.out.println("Connection Terminated");
            return con;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static void main(String args[]){
        TestConnection t = new TestConnection();
        t.testConnection();
    }

}

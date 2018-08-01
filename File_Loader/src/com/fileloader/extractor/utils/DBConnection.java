package com.fileloader.extractor.utils;

import java.sql.*;


public class DBConnection {

    public static Connection con = null;
    static {
        try{

            JobProperties jp = new JobProperties("D://File_Loader//src//job.properties");

            String host = jp.getValue("db.hostname", "root@localhost");
            String username = jp.getValue("db.user", "root");
            String password = jp.getValue("db.password", "secret");
            String schema = jp.getValue("db.schema.name", "DBO");

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(host, username,password);
        }
        catch (SQLException e){
            System.out.println("failed - exception: " + e.getMessage());
        }
        catch (Exception e){
            System.out.println("failed - exception: " + e.getMessage());
        }
    }

    public Connection getConnection(){
        return con;
    }

}

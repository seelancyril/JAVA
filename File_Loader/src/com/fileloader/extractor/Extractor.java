package com.fileloader.extractor;

import com.fileloader.extractor.utils.DBConnection;

import java.sql.*;
import java.util.*;

public class Extractor {
    private static Connection con = null;
    private static ArrayList<String> tables = new ArrayList<String>();

    public Extractor(String schema){
        try {
            DBConnection connection = new DBConnection();
            con = connection.getConnection();
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select table_name from information_schema.tables where table_schema='"+schema+"'");
            while(rs.next()){
                tables.add(rs.getString(1));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void runExtractor(){
        System.out.println(tables);
    }

    public void countTableRows(){
        System.out.println("Getting row counts from the database schema");
    }
}

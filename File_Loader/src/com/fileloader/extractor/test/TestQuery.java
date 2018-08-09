package com.fileloader.extractor.test;

import com.fileloader.extractor.Extractor;
import java.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class TestQuery {
    public static void main(String args[]){
        try{
            TestConnection t1 = new TestConnection();
            Connection con1 = t1.testConnection();
            Statement stmt=con1.createStatement();
            ResultSet rs=stmt.executeQuery("select * from actor");
            Map<Integer, String> columnNames = new LinkedHashMap<Integer, String>();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colcount = rsmd.getColumnCount();
            //Getting the column names for each table
            while(rs.next()){
                for(int i=1; i<=colcount;i++){

                    columnNames.put(i, Extractor.formatName(rsmd.getColumnName(i)));
                    Object columnvalue = rs.getObject(i).toString().trim();
                    byte[] bytes =columnvalue.toString().getBytes();
                    String data = new String(bytes);
                    System.out.println(Extractor.formatName(rsmd.getColumnName(i)) + " "+ data);
                }
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

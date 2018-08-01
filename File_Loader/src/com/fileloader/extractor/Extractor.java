package com.fileloader.extractor;
/*
* Extractor extracts data from mysql database
* converts the data into XML file
* Also creates one csv file with the table row counts
 */
import com.fileloader.extractor.utils.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.io.FileWriter;

public class Extractor {
    private static Connection con = null;
    private static Map<String, Integer> tables = new HashMap<String, Integer>();
    private static ResultSet rs = null;

    public Extractor(String schema){
        try {
            DBConnection connection = new DBConnection();
            con = connection.getConnection();
            Statement stmt=con.createStatement();
            rs=stmt.executeQuery("select table_name,table_rows from information_schema.tables where table_schema='"+schema+"'");
            while(rs.next()){
                tables.put(rs.getString(1), rs.getInt(2));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void runExtractor(){

    }

    public void countTableRows(){
        try{
            System.out.println("Generating csv");
            generateCSV();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    static void generateCSV() throws IOException {
        final String COMMA_DELIMITER = ",";
        final String NEW_LINE_SEPARATOR = "\n";
        final String FILE_HEADER = "Table_Name,Count";
        FileWriter fw = null;
        try {
            fw = new FileWriter("D://ETL//row_count.csv");
            fw.append(FILE_HEADER);
            fw.append(NEW_LINE_SEPARATOR);
            for(Map.Entry<String, Integer> table: tables.entrySet()){
                fw.append(table.getKey());
                fw.append(COMMA_DELIMITER);
                fw.append(table.getValue().toString());
                fw.append(NEW_LINE_SEPARATOR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            fw.flush();
            fw.close();
        }
    }

}

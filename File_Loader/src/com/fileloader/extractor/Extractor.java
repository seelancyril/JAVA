package com.fileloader.extractor;
/*
* Extractor extracts data from mysql database
* converts the data into XML file
* Also creates one csv file with the table row counts
 */
import com.fileloader.extractor.utils.DBConnection;
import com.fileloader.extractor.utils.JobProperties;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Document;

public class Extractor {
    private static Connection con = null;
    private static Map<String, Integer> tables = new HashMap<String, Integer>();
    private static Statement stmt = null;
    private static Statement stmtCount = null;
    private static ResultSet rs = null;
    private static ResultSetMetaData rs_columns = null;
    private String tableschema;

    public Extractor(String schema){
        try {
            this.tableschema = schema;
            DBConnection connection = new DBConnection();
            con = connection.getConnection();
            this.stmt=con.createStatement();
            this.stmtCount=con.createStatement();
            this.rs=stmt.executeQuery("select table_name,table_rows from information_schema.tables where table_schema='"+tableschema+"'");
            while(rs.next()){
                tables.put(rs.getString(1), rs.getInt(2));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void runExtractor(JobProperties jp) throws Exception{
        Map<Integer, String> columnNames = new LinkedHashMap<Integer, String>();
        for(String tablename : tables.keySet() ){
            String tablename_formatted = formatName(tablename);
            ResultSet tablerowcount = stmtCount.executeQuery("select count(*) from" + tablename);
            rs = stmt.executeQuery("select * from "+ tablename);
            int rowcount = 0;
            while (tablerowcount.next()){
                rowcount = tablerowcount.getInt(1);
            }
            rs_columns = rs.getMetaData();
            int colcount = rs_columns.getColumnCount();
            System.out.println("Extraction started for table: "+tablename_formatted);
            String filepath = jp.getValue("app.outputfiles.location", "");
            for (int chunk=0;chunk<rowcount;chunk++){
                String filename = filepath + tableschema.toUpperCase() + "-" + tablename.toUpperCase() + "-" + String.format("%04d", chunk) + ".xml";
                Writer out = new OutputStreamWriter(new FileOutputStream(filename));
                XMLOutputFactory factory = XMLOutputFactory.newInstance();
                XMLStreamWriter writer = factory.createXMLStreamWriter(out);

                writer.writeStartDocument("UTF-8","1.0");
                writer.writeCharacters("\n");
                writer.writeStartElement(tableschema.toUpperCase());
                writer.writeCharacters("\n\t");
                writer.writeStartElement(tablename.toUpperCase());
                int counter = 1;
                while(rs.next() && counter<100){
                    writer.writeCharacters("\n\t\t");
                    writer.writeStartElement("ROW");
                    for(int i=1; i<=colcount;i++) {
//                    columnNames.clear();
//                    columnNames.put(i, Extractor.formatName(rs_columns.getColumnName(i)));
                        Object columnvalue = rs.getObject(i) == null ? "" :rs.getObject(i).toString().trim();
                        byte[] bytes = columnvalue.toString().getBytes();
                        String data = new String(bytes);
                        writer.writeCharacters("\n\t\t\t");
                        writer.writeStartElement(rs_columns.getColumnName(i).toUpperCase().replace(" ","_"));
                        writer.writeCharacters(data);
                        writer.writeEndElement();
                    }
                    writer.writeCharacters("\n\t\t");
                    writer.writeEndElement();
                    counter ++;
                }

                writer.writeCharacters("\n\t");
                writer.writeEndElement();
                writer.writeCharacters("\n");
                writer.writeEndElement();
                writer.writeEndDocument();
                writer.flush();
                writer.close();
                if(out != null){
                    out.flush();
                    out.close();
                }
            }
        }
    }

    public void countTableRows(){
        try{
            System.out.println("Generating table rowcount csv");
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
            System.out.println("CSV file generated and stored in D:\\ETL\\row_count.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            fw.flush();
            fw.close();
        }
    }

    public static String formatName(String string) {
        string = string.trim().replace("$", "").replace("(", "").replace(")", "").replace("[", "")
                .replace("]", "").replace("{", "").replace("}", "").replace("/", "").replace("\\", "").replace("\"", "")
                .replace("'", "").replace("`", "").replace("&", "").replace("@", "").replace("#", "")
                .replace("%", "").replace("!", "").replace("^", "").replace("*", "").replace("|", "")
                .replaceAll("\\s+", "_").toUpperCase();
        return
                (string.charAt(0) >= '0' && string.charAt(0) <= '9')?"_"+ string:string;
    }

}

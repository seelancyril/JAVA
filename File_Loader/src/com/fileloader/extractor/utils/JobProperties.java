package com.fileloader.extractor.utils;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;

public class JobProperties {

    private Properties properties;

    public JobProperties(String filepath){
        properties = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(filepath);
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) in.close();
            }
            catch (Exception e) { }
        }
    }

    public  JobProperties(){
        System.out.println("Hey you need to pass the file path while creating objects for this class!!");
        System.exit(0);
    }

    public String getValue(String keyName, String defaultValue) {
        String result;
        result = properties.getProperty(keyName, defaultValue);
        return result;
    }

}

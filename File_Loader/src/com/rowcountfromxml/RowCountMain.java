package com.rowcountfromxml;

import java.io.File;

public class RowCountMain {

    public static void main(String args[]){
        try{
            if(args.length == 2){
                System.out.println("Valid command");
                File xmldir = new File(args[0]);
                if(xmldir.isDirectory()){
                    RowCounter job = new RowCounter(xmldir);
                }
                else {
                    System.out.println("invalid directory.");
                    System.exit(0);
                }
            }
            else{
                System.out.println("============usage==================");
                System.out.println("<Folder path> <record tag>");
                System.exit(0);
            }
        }
        catch (Exception e){
            System.out.println("Exception Message: "+ e.getMessage());
        }
    }

}

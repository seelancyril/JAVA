package com.filehandler;

import java.io.*;
import java.util.*;

class FileRead{

	public static void main(String args[]){
		try{
			File f = new File("resources");
			System.out.println("name: " + f.getName());
			System.out.println("Path: "+f.getPath());
	        System.out.println("Absolute path:" +f.getAbsolutePath());
	        System.out.println("Parent:"+f.getParent());
	        System.out.println("Exists :"+f.exists());
	        if(f.isDirectory()==true){
	        	System.out.println("----iterating the directory-----");
	        	String files[] = f.list();
	        	System.out.println(files.length);
	        	for (String a:files){
	        		System.out.println(a);
	        	}
	        }
	        else{
	        	File f1 = new File(f.getAbsolutePath());
	        	
	        	System.out.println("this is the directory: " + f1.getParent());
	        }
		}
		catch(Exception e){
			System.out.println(e);
		}
		finally{
			System.out.println("------Process completed---------");
		}
	}
}
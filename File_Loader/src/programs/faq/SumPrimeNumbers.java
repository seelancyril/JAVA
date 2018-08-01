package com.faq;

import java.util.*;
import java.lang.*;
import java.io.*;

class SumPrimeNumbers {
	public static void main (String[] args) {
		//code
		Scanner test = new Scanner(System.in);
		int n = test.nextInt();
		for (int k=0; k<n;k++){
		    int val = test.nextInt();
		    int i;
            int num = 0;
            int primeNumbers = 0;
        
            for (i = 1; i <= val; i++){ 		  	  
                int counter=0; 	  
                for(num = i; num >= 1; num--){
                    if(i%num==0){
         		        counter = counter + 1;
        	        }
        	    }
        	    if (counter ==2){
        	     primeNumbers = primeNumbers + i ;
        	    }	
            }	
            System.out.println(primeNumbers);
		}
		test.close();
	}
}
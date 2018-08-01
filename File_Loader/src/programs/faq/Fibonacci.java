package com.faq;

import java.io.*;
import java.util.*;

public class Fibonacci {
	public static void main(String args[]){
		System.out.println("Fibonacci Series");
		int first=0, second=1, i, nTimes=10;
		System.out.print(first + " " + second);
		for (i=2; i<=nTimes; i++){
			int fnum = first + second;
			System.out.print(" " + fnum);
			first = second;
			second = fnum;
		}
	}
}

package com.faq;

public class Factorial {
	public static void main(String[] args) {
		int n = 1, i;
		int fact = 1;
		for(i = 1; i<=n; i++){
			fact = fact * i;
		}
		System.out.println(fact);
	}
}

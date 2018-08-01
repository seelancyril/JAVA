package com.faq;

public class Palindrome {
	
	public static void main(String args[]){
		String s = "oops";
		int num = 3573343, i, sum = 0, remin;
		int temp = num;
		//Number reversing
		while(num > 0){
			remin = num % 10; 			// 2; 	3
			sum = (sum * 10) + remin; 	// 0+2; 20 + 3;
			num = num/10; 				// 3; 	0
		}
		
		if (temp == sum){
			System.out.println("Palindrome");
		}
		else{
			System.out.println("Not Palindrome");
		}
		stringReverse(s);
	}
	//Method for string reverse
	private static void stringReverse(String s) {
		String reversedString = "";
		for (int j=s.length()-1; j >= 0; j--){
			reversedString = reversedString + s.charAt(j);
		}
		System.out.println(reversedString);
	}
}

package com.faq;

public class ArmstrongNumber {
	public static void main(String[] args) {
		int n = 122, reminder, arm = 0;
		int temp = n;
		while (n > 0){
			reminder = n%10;
			n = n/10;
			arm = arm + (reminder * reminder * reminder);
		}
		if (temp == arm){
			System.out.println("yes! Its an Armstrong number");
		}
		else{
			System.out.println("No!! Its not");
		}
	}
}

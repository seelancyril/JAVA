package com.concepts;

class Parent{
	int id = 10001;
	// Method overloading
	void printMe(String s){
		System.out.println("Method");
	}
	
	void printMe(String s, String type){
		System.out.println("Method Overloading");
	}
}

public class Inheritance extends Parent{
	public static void main(String[] args) {
		Inheritance I = new Inheritance();
		System.out.println(I.id);
		I.printMe("working");
		I.printMe("working", "yes");
	}
}

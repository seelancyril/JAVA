package com.faq;

class Student{
	int age;
	String name;
	
	Student(String name, int age){
		this.name = name;
		this.age = age;
	}
	
	void displayInformation(){
		System.out.println("Name :" + name);
		System.out.println("age  :" + age);
		
	}
}

public class ClassMethods {
	
	public static void main(String[] args) {
		Student s1 = new Student("Seelan", 23);
		Student s2 = new Student("John", 23);
		s1.displayInformation();
		s2.displayInformation();
		
	}
}

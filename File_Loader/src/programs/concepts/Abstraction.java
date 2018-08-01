package com.concepts;

abstract class Upload{ 
	abstract void loadFile();
	abstract void analyseFile();
}

class ActualLoader extends Upload{
	void loadFile() {
		System.out.println("this is for loading");
	}
	
	void analyseFile() {
		System.out.println("this is for analysing");
	}
}

public class Abstraction{
	public static void main(String[] args) {
		Upload u;
		u = new ActualLoader();
		u.loadFile();
	}
}

package main;

import java.util.Scanner;

import locations.Mall;

public class STMain {
	
	public static void main(String[] args) {
		System.out.println("What's your name?");
		Scanner scanner = new Scanner(System.in);
		Mall mall = Mall.DEFAULT_MALL;
		Shopper shopper = new Shopper(scanner.next(), 6000);
		
		shopper.visit(mall);
		scanner.close();
	}
}

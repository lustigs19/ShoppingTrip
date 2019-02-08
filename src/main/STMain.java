package main;

import java.util.Scanner;

import locations.Mall;

public class STMain {
	
	public static void main(String[] args) {
		System.out.println("What's your name?");
		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();
		
		Float bal = null;
		do {
			System.out.println("Starting balance?");
			String balance = scanner.next();
			
			try {
				bal = Float.parseFloat(balance);
			} catch (NumberFormatException e) {
				
			}
		} while (bal == null);
		
		Mall mall = Mall.DEFAULT_MALL;
		Shopper shopper = new Shopper(name, bal);
		
		shopper.visit(mall);
		scanner.close();
	}
}

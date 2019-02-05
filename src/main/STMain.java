package main;

import locations.Mall;

public class STMain {
	
	public static void main(String[] args) {
		Mall mall = Mall.DEFAULT_MALL;
		Shopper shopper = new Shopper("Stuart", 100);
		
		shopper.visit(mall);
	}
}

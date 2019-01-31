package main;

import locations.Mall;

public class STMain {
	
	public void main() {
		Mall mall = Mall.NATICK_MALL;
		Shopper shopper = new Shopper("Stuart", 6000);
		
		shopper.visit(mall);
	}
}

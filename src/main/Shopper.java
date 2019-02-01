package main;

import locations.Area;
import locations.Location;
import locations.Mall;

public class Shopper {
	String name;
	float balance;
	Mall mall;
	
	boolean finished;
	
	public Shopper(String n, float bal) {
		name = n;
		balance = bal;
		finished = false;
	}
	
	public void visit(Location l) {
		if (l instanceof Mall) {
			mall = (Mall) l;
			Location currentLocation = mall.getDefaultArea();
			
			while (!finished) {
				visit(currentLocation);
			}
		} else if (l instanceof Area) {
			Menu areaMenu = new Menu("Where would you like to visit?", "Stores", "Services", "Areas");
			switch(areaMenu.displayAndChoose()) {
				
			}
		}
	}
}

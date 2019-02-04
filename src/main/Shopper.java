package main;

import java.util.ArrayList;

import locations.Area;
import locations.Connection;
import locations.Hotspot;
import locations.Location;
import locations.Mall;
import locations.Store;

public class Shopper {
	String name;
	float balance;
	Mall mall;
	
	Location currentLocation;
	boolean finished;
	
	public Shopper(String n, float bal) {
		name = n;
		balance = bal;
		finished = false;
	}
	
	public void visit(Location l) {
		int choice;
		
		if (l instanceof Mall) {
			mall = (Mall) l;
			currentLocation = mall.getDefaultArea();
			
			while (!finished) {
				visit(currentLocation);
			}
			System.out.println("Final balance: " + balance);
		} else if (l instanceof Area) {
			Menu menu1 = new Menu("You are in the area '" + currentLocation.getName() + "'.\n" +
						"Where would you like to visit?", "Stores", "Services", "Other areas");
			switch(menu1.displayAndChoose()) {
			default:
			case 1:
				menu1.reset();
				menu1.setTitle("Choose a store:");
				ArrayList<Store> storeList = ((Area) currentLocation).getStores();
				for (Store store : storeList) {
					menu1.addItem(store.getName());
				}
				menu1.addItem("cancel");
				choice = menu1.displayAndChoose();
				if (choice < storeList.size()) {
					currentLocation = storeList.get(choice - 1);
				}
				break;
			case 2:
				
				break;
			case 3:
				menu1.reset();
				ArrayList<Area> areaList = new ArrayList<Area>();
				for (Connection c : mall.getConnections((Area) currentLocation)) {
					menu1.addItem(c.get((Area) currentLocation).getName());
					areaList.add(c.get((Area) currentLocation));
				} // TODO set up a menu for locations!!!
				menu1.addItem("cancel");
				choice = menu1.displayAndChoose();
				if (choice < areaList.size()) {
					currentLocation = areaList.get(choice - 1);
				}
				break;
			}
		} else if (l instanceof Hotspot) {
			
		}
	}
}

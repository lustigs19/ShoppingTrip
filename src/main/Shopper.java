package main;

import java.util.ArrayList;

import locations.Area;
import locations.Connection;
import locations.Location;
import locations.Mall;
import locations.Service;
import locations.Store;

public class Shopper {
	String name;
	float balance;
	Mall mall;
	ArrayList<Item> purchasedItems;
	
	Area currentArea;
	boolean finished;
	
	public Shopper(String n, float bal) {
		name = n;
		balance = bal;
		finished = false;
		
		purchasedItems = new ArrayList<Item>();
	}
	
	public void visit(Location l) {
		Location loc;
		
		if (l instanceof Mall) {
			mall = (Mall) l;
			currentArea = mall.getDefaultArea();
			System.out.printf("Your balance: $%.2f\n", balance);
			while (!finished) {
				visit(currentArea);
			}
			System.out.println("Final balance: " + balance);
		} else if (l instanceof Area) {
			Menu menu = new Menu("You are in the area '" + currentArea.getName() + "'.\n" +
						"Where would you like to visit?", "Stores", "Services", "Other areas");
			// TODO check balance
			switch(menu.displayAndChoose()) {
			
			default:
			case 1:
				menu = new LocationMenu("Choose a store:", currentArea.getHotspots(Store.class));
				break;
			case 2:
				menu = new LocationMenu("Choose a service:", currentArea.getHotspots(Service.class));
				break;
			case 3:
				ArrayList<Location> tempList = new ArrayList<Location>();
				for (Connection c : mall.getConnections(currentArea)) {
					tempList.add(c.get(currentArea));
				}
				menu = new LocationMenu("Choose an area:", tempList);
				break;
			}
			loc = ((LocationMenu) menu).getLocation(menu.displayAndChoose());
			if (loc != null) {
				if (loc instanceof Area) {
					currentArea = (Area) loc;
				} else {
					visit(loc);
				}
			}
		} else if (l instanceof Store) {
			ItemMenu menu = new ItemMenu("You are in " + l.getName() + ".\nWhat would you like to purchase?", ((Store) l).getItemList());
			Item itemChoice = menu.getChoice(menu.displayAndChoose());
			
			if (itemChoice != null) {
				if (balance >= itemChoice.getPrice()) {
					balance -= itemChoice.getPrice();
					purchasedItems.add(itemChoice);
					System.out.printf("You spent $%.2f on a " + itemChoice.getName() + ".\n" +
								"You have $%.2f remaining.\n", itemChoice.getPrice(), balance);
				} else {
					System.out.println("You do not have enough money to buy this. Go to an ATM and receive more money, "
							+ "or return to the entrance to finish your trip.");
				}
				visit(l);
			}
		}
	}
}

package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import locations.Area;
import locations.Location;
import locations.Mall;
import locations.Service;
import locations.Store;

public class Shopper {
	String name;
	float balance;
	Mall mall;
	ArrayList<Purchase> purchaseHistory;
	ArrayList<Item> inventory;
	
	public static final int HISTORY_COLUMN1_WIDTH = 30;
	public static final int HISTORY_COLUMN2_WIDTH = 6;
	public static final String HISTORY_TITLE = "***PURCHASE HISTORY***";
	
	static final HashSet<Character> VOWELS = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
	Area currentArea;
	boolean finished;
	
	public Shopper(String n, float bal) {
		name = n;
		balance = bal;
		finished = false;
		
		purchaseHistory = new ArrayList<Purchase>();
		inventory = new ArrayList<Item>();
	}
	
	public void visit(Location l) {
		Location loc;
		
		if (l instanceof Mall) {
			mall = (Mall) l;
			System.out.println("You are entering the " + l.getName());
			currentArea = mall.getDefaultArea();
			System.out.printf("Your balance: $%.2f\n", balance);
			
			// loop of areas. Stores are visited inside the loop of a specific area (not their own visit)
			while (!finished) {
				visit(currentArea);
			}
			System.out.printf("Final balance: $%.2f", balance);
			
		} else if (l instanceof Area) {
			Menu menu = new Menu("You are in the area '" + currentArea.getName() + "'.\n" +
						"Where would you like to visit?", "Stores", "Services", "Other areas", "Purchase history", "Current balance");
			if (currentArea == mall.getDefaultArea()) menu.addItem("Exit mall");
			switch(menu.displayAndChoose()) {
			
			default:
				break;
			case 1:
				menu = new LocationMenu("Choose a store:", currentArea.getHotspots(Store.class));
				break;
			case 2:
				menu = new LocationMenu("Choose a service:", currentArea.getHotspots(Service.class));
				break;
			case 3:
				menu = new LocationMenu("Choose an area:", mall.getConnectedAreas(currentArea));
				break;
			case 4:
				printPurchaseHistory();
				break;
			case 5:
				System.out.printf("Current balance: $%.2f\n", balance);
				break;
			case 6:
				finished = true;
				break;
			}
			
			// if you have chosen options 1, 2, or 3
			if (menu instanceof LocationMenu) {
				loc = ((LocationMenu) menu).getLocation(menu.displayAndChoose());
				if (loc != null) {
					if (loc instanceof Area) {
						currentArea = (Area) loc;
					} else {
						visit(loc);
					}
				}
			}
		} else if (l instanceof Store) {
			ItemMenu menu = new ItemMenu("You are in " + l.getName() + ".\nWhat would you like to purchase?", ((Store) l).getItemMap());
			Purchase itemChoice = menu.getChoice(menu.displayAndChoose());
			
			if (itemChoice != null) {
				Item i = itemChoice.getItem();
				if (balance >= itemChoice.getCost()) {
					balance -= itemChoice.getCost();
					System.out.printf("You spent $%.2f on a" +
							(VOWELS.contains(Character.isAlphabetic(i.getName().charAt(0)) ? // checking for "an" or "a"
							Character.toLowerCase(i.getName().charAt(0)) :
							Character.toLowerCase(i.getName().charAt(1))) ? "n" : "") +
							" " + i.getName() + ".\n" +
							"You have $%.2f remaining.\n", itemChoice.getCost(), balance);
					
					if (i instanceof FoodItem) {
						System.out.println("\n***" + name + ": " + ((FoodItem) i).getText() + "***\n");
					} else {
						inventory.add(i);
					}
					purchaseHistory.add(itemChoice);
				} else {
					System.out.println("You do not have enough money to buy this.\n"
							+ "Go to an ATM and receive more money, \n"
							+ "or return to the entrance to finish your trip.");
				}
				// revisit the store. Only return to area if 'return' is chosen from the menu.
				visit(l);
			}
		} else if (l instanceof Service) {
			// TODO
		}
	}
	
	public void printPurchase(Purchase p) {
		StringBuilder sb = new StringBuilder();
		if (p.getItem().getName().length() <= HISTORY_COLUMN1_WIDTH) {
			sb.append(p.getItem().getName());
			for (int i = p.getItem().getName().length(); i < HISTORY_COLUMN1_WIDTH; i++) sb.append(" ");
		} else {
			sb.append(p.getItem().getName().substring(0, HISTORY_COLUMN1_WIDTH));
		}
		sb.append(String.format("$%.2f", p.getCost()));
		System.out.println(sb.toString());
	}
	
	public void printPurchaseHistory() {
		if (purchaseHistory.size() > 0) {
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0; i < Math.ceil((HISTORY_COLUMN1_WIDTH + HISTORY_COLUMN2_WIDTH - HISTORY_TITLE.length()) / 2); i++) sb.append(" ");
			System.out.println(sb.toString() + HISTORY_TITLE + sb.toString()); // a bunch of ' 's on either side
			sb.delete(0, sb.length());
			sb.append("Item:");
			for (int i = 0; i < HISTORY_COLUMN1_WIDTH - "Item:".length(); i++) {
				sb.append(" ");
			}
			sb.append("Cost:");
			System.out.println(sb.toString());
			
			for (Purchase p : purchaseHistory) {
				printPurchase(p);
			}
			System.out.println();
		} else {
			System.out.println("You have yet to purchase anything.");
		}
	}
}

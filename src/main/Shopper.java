package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import locations.Area;
import locations.Location;
import locations.Mall;
import locations.Map;
import locations.Store;

public class Shopper {
	String name;
	float balance;
	Mall mall;
	ArrayList<Purchase> purchaseHistory;
	ArrayList<Item> inventory;
	Scanner scanner = new Scanner(System.in);
	
	public static final int HISTORY_COLUMN1_WIDTH = 30;
	public static final int HISTORY_COLUMN2_WIDTH = 8;
	public static final String HISTORY_TITLE = "*** PURCHASE HISTORY ***";
	
	static final HashSet<Character> AN_CHARS = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u', '8'));
	Area currentArea;
	boolean finished;
	
	public Shopper(String n, float bal) {
		name = n;
		balance = bal;
		finished = false;
		currentArea = null;
		
		purchaseHistory = new ArrayList<Purchase>();
		inventory = new ArrayList<Item>();
	}
	
	public void visit(Mall l) {
		mall = (Mall) l;
		System.out.println("You are entering the " + l.getName());
		currentArea = mall.getDefaultArea();
		printCurrentBalance();
		
		if (currentArea == null || mall == null) finished = true;
		
		// loop of areas. Stores are visited inside the loop of a specific area (not their own visit)
		while (!finished) {
			visit(currentArea);
		}
		System.out.printf("Final balance: $%.2f\n", balance);
	}
			
	public void visit(Area l) {
		Location loc;
		Menu menu = new Menu("You are in the area '" + currentArea.getName() + "'.\n" +
				"What would you like to do?", 				// title
				"Visit hotspots (ex: stores)",					// 1
				"Visit other areas",							// 2
				"Check purchase history",						// 3
				"Check current balance");						// 4
		if (((Area) l).isEntrance()) menu.addItem("Exit mall"); // 5
		switch(menu.displayAndChoose()) {
		
		default:
			break;
		case 1:
			menu = new LocationMenu("Choose a hotspot:", currentArea.getHotspots());
			break;
		case 2:
			menu = new LocationMenu("Choose an area:", mall.getConnectedAreas(currentArea));
			break;
		case 3:
			printPurchaseHistory();
			break;
		case 4:
			printCurrentBalance();
			break;
		case 5:
			finished = true;
			break;
		}
		
		// if you have chosen options 1 or 2
		if (menu instanceof LocationMenu && currentArea != null) {
			loc = ((LocationMenu) menu).getLocation(menu.displayAndChoose());
			if (loc != null) {
				if (loc instanceof Area) {
					currentArea = (Area) loc;
				} else if (loc instanceof Store) {
					visit((Store)loc);
				} else if (loc instanceof Map) {
					visit((Map) loc);
				}
			}
		}
	}

	public void visit(Store l) {
		ItemMenu menu = new ItemMenu("You are in " + l.getName() + ".\nWhat would you like to purchase?", ((Store) l).getItemMap());
		Purchase itemChoice = menu.getChoice(menu.displayAndChoose());
		
		if (itemChoice != null) {
			Item i = itemChoice.getItem();
			if (balance >= itemChoice.getCost()) {
				balance -= itemChoice.getCost();
				
				boolean an;
				if (AN_CHARS.contains(i.getName().charAt(0)) ||
						(i.getName().length() > 1 &&
						!Character.isAlphabetic(i.getName().charAt(0)) &&
						AN_CHARS.contains(i.getName().charAt(1)))) {
					an = true;
				} else {
					an = false;
				}
				
				System.out.printf("You spent $%.2f on a" +
						(an ? "n" : "") +
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
						+ "return to the entrance to finish your trip, or continue to look around.");
			}
			
			// revisit the store. Only return to area if 'return' is chosen from the menu.
			visit(l);
		}
	}
	
	public void visit(Map l) {
		boolean leave = false;
			
		System.out.println("MAP: 'Welcome to the " + mall.getName() + " map service.'\n\n"
				+ "Input location name (type 'return' to return to " + currentArea.getName() + "):");
		String input = scanner.nextLine();
		System.out.println();
		if (input.equals("return")) {
			leave = true;
		} else if (mall.getLocation(input) != null) {
			if (mall.getLocation(input).equals(mall) || mall.getLocation(input).equals(currentArea) || mall.getLocation(input) instanceof Map) {
				System.out.println("You are currently in this location.");
			} else {
				
				System.out.println("Directions:");
				ArrayList<Location> directions = ((Map) l).getShortestRoute(mall, currentArea, mall.getLocation(input));
				for (int i = 0; i < directions.size(); i++) {
					System.out.print(directions.get(i).getName() + (i == directions.size() - 1 ? "" : " -> "));
				}
			}
			
			System.out.print("\n\n");
		} else {
			System.out.println("MAP: 'I am sorry but we do not have that location here.'\n");
		}
		
		if (!leave) {
			visit(l);
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
			StringBuilder line = new StringBuilder();
			for (int i = 0; i < HISTORY_COLUMN1_WIDTH + HISTORY_COLUMN2_WIDTH; i++) {
				line.append("-");
			}
			line.append("\n");
			
			System.out.print(line);
			for (int i = 0; i < Math.ceil((HISTORY_COLUMN1_WIDTH + HISTORY_COLUMN2_WIDTH - HISTORY_TITLE.length()) / 2); i++) sb.append(" ");
			System.out.println(sb.toString() + HISTORY_TITLE + sb.toString()); // a bunch of ' 's on either side
			sb.delete(0, sb.length()); // clears sb
			sb.append("Item:");
			for (int i = 0; i < HISTORY_COLUMN1_WIDTH - "Item:".length(); i++) {
				sb.append(" ");
			}
			sb.append("Cost:");
			System.out.print(line.toString() + sb.toString() + "\n" + line.toString());
			
			for (Purchase p : purchaseHistory) {
				printPurchase(p);
			}
			System.out.println(line);
		} else {
			System.out.println("You have yet to purchase anything.");
		}
	}
	
	public void printCurrentBalance() {
		System.out.printf("Current balance: $%.2f\n", balance);
	}
}

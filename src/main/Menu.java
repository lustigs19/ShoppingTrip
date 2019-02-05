package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
	Scanner sc = new Scanner(System.in);
	ArrayList<String> items;
	String menuTitle;
	int MENU_ITEM_ERROR = -1;
	
	public Menu(String title, String... args) {
		menuTitle = title;
		
		items = new ArrayList<String>();
		for (String arg : args) {
			items.add(arg);
		}
	}
	
	public void setTitle(String title) {
		menuTitle = title;
	}
	
	public void addItem(String arg) {
		items.add(arg);
	}
	
	public void displayMenuItem(int i) {
		System.out.println((i + 1) + ": " + items.get(i));
	}
	
	public int displayAndChoose() {
		boolean valid = false;
		int choice;
		do {
			System.out.println(menuTitle);
			for (int i = 0; i < items.size(); i++) {
				displayMenuItem(i);
			}
			String input = sc.next();
			System.out.println();
			try {
				choice = Integer.valueOf(input);
			} catch (Exception e) {
				choice = MENU_ITEM_ERROR;
			}
			valid = choice > 0 && choice <= items.size();
		} while (!valid);
		
		return choice;
	}
	
	/** empties the menu */
	public void reset() {
		items = new ArrayList<String>();
	}
	
	public int getMenuSize() {
		return items.size();
	}
}

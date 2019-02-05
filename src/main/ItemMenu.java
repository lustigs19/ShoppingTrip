package main;

import java.util.ArrayList;

public class ItemMenu extends Menu {
	
	ArrayList<Item> itemList;
	
	public ItemMenu(String title, ArrayList<Item> args) {
		super(title);
		
		itemList = args;
		
		items = new ArrayList<String>();
		for (Item arg : itemList) {
			items.add(arg.getName() + ": $" + String.format("%.2f", arg.getPrice()));
		}
		items.add("return");
	}
	
	public Item getChoice(int choice) {
		if (choice - 1 == itemList.size()) return null;
		return itemList.get(choice - 1);
	}

}

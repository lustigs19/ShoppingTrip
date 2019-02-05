package main;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemMenu extends Menu {
	
	ArrayList<Purchase> purchaseList;
	
	public ItemMenu(String title, HashMap<Item, Float> itemMap) {
		super(title);
		
		purchaseList = new ArrayList<Purchase>();
		for (Item i : itemMap.keySet()) {
			purchaseList.add(new Purchase(i, itemMap.get(i)));
		}
		
		items = new ArrayList<String>();
		for (Purchase p : purchaseList) {
			items.add(p.getItem().getName() + ": $" + String.format("%.2f", p.getCost()));
		}
		items.add("return");
	}
	
	public Purchase getChoice(int choice) {
		if (choice - 1 == purchaseList.size()) return null;
		return purchaseList.get(choice - 1);
	}

}

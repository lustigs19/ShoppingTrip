package locations;

import java.util.HashMap;

import main.Item;

public class Store extends Hotspot {
	
	HashMap<Item, Float> itemMap;
	
	public Store(String n, HashMap<Item, Float> items) {
		super(n);
		itemMap = items;
	}
	
	public void addItem(Item item, Float cost) {
		itemMap.put(item, cost);
	}
	
	public HashMap<Item, Float> getItemMap() {
		return itemMap;
	}

}

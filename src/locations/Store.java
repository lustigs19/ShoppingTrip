package locations;

import java.util.ArrayList;
import java.util.Arrays;

import main.Item;

public class Store extends Hotspot {
	
	ArrayList<Item> items;
	
	public Store(String n, Item... itemArray) {
		super(n);
		
		items = new ArrayList<Item>();
		items.addAll(Arrays.asList(itemArray));
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public ArrayList<Item> getItemList() {
		return items;
	}

}

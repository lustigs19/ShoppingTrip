package main;

import java.util.ArrayList;

import locations.Location;

public class LocationMenu extends Menu {
	
	ArrayList<Location> locations;
	
	public LocationMenu(String title, ArrayList<Location> args) {
		super(title);
		
		locations = args;
		
		items = new ArrayList<String>();
		for (Location arg : locations) {
			items.add(arg.getName());
		}
		items.add("cancel");
	}
	
	public Location getLocation(int choice) {
		if (choice - 1 == locations.size()) return null;
		return locations.get(choice - 1);
	}
}

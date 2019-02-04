package locations;

import java.util.ArrayList;

public class Area extends Location {
	
	ArrayList<Hotspot> hotspots;

	public Area(String n) {
		super(n);
		hotspots = new ArrayList<Hotspot>();
	}
	
	public void addHotspot(Hotspot h) {
		hotspots.add(h);
	}
	
	public ArrayList<Store> getStores() {
		ArrayList<Store> tempList = new ArrayList<Store>();
		for (Hotspot h : hotspots) {
			if (h instanceof Store) {
				tempList.add((Store) h);
			}
		}
		
		return tempList;
	}
}

package locations;

import java.util.ArrayList;

public class Area extends Location {
	
	ArrayList<Hotspot> hotspots;
	boolean entrance;

	public Area(String n, boolean isEntrance) {
		super(n);
		hotspots = new ArrayList<Hotspot>();
		entrance = isEntrance;
	}
	
	public void addHotspot(Hotspot h) {
		hotspots.add(h);
	}
	
	/** returns all the hotspots */
	public ArrayList<Location> getHotspots() {
		ArrayList<Location> tempList = new ArrayList<Location>();
		for (Hotspot h : hotspots) {
			tempList.add(h);
		}
		
		return tempList;
	}
	
	/** returns all hotspots of class c */
	public ArrayList<Location> getHotspots(Class<?> c) {
		ArrayList<Location> tempList = new ArrayList<Location>();
		
		for (Hotspot h : hotspots) {
			if (c.isInstance(h)) {
				tempList.add(h);
			}
		}
		
		return tempList;
	}
	
	public boolean isEntrance() {
		return entrance;
	}
}

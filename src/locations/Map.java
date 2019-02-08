package locations;

import java.util.ArrayList;

public class Map extends Hotspot {
	
	ArrayList<Location> paths;

	public Map() {
		super("Map");
		paths = new ArrayList<Location>();
	}
	
	public ArrayList<Location> getShortestRoute(Mall m, Area a, Location b) {
		// TODO and maps should be able to have their own names :(
		return findLocation(m, a, b, new ArrayList<Location>());
	}
	
	/** returns 1 possible path to a certain location from a certain area.*/
	public ArrayList<Location> findLocation(Mall m, Area currentLoc, Location destination, ArrayList<Location> visitedLocations) {
		
		visitedLocations.add(currentLoc);
		
		if (currentLoc.equals(destination)) {
			return visitedLocations;
		}
		
		for (Location h : currentLoc.getHotspots()) {
			if (h.equals(destination)) {
				visitedLocations.add(destination);
				return visitedLocations;
			}
		}
		
		ArrayList<Location> nextLocs = new ArrayList<Location>();
		for (Location l : m.getConnectedAreas(currentLoc)) {
			if (!visitedLocations.contains(l)) {
				nextLocs.add(l);
			}
		}
		
		if (nextLocs.size() == 0) {
			return null;
		}
		
		for (Location l : nextLocs) {
			ArrayList<Location> result = findLocation(m, (Area) l, destination, new ArrayList<Location>(visitedLocations));
			if (result != null) return result;
		}
		
		return null;
	}
}

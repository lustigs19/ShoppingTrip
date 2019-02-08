package locations;

import java.util.ArrayList;

public class Map extends Hotspot {

	public Map(String n) {
		super(n);
	}
	
	public ArrayList<Location> getShortestRoute(Mall m, Area a, Location b) {
		ArrayList<ArrayList<Location>> paths = new ArrayList<ArrayList<Location>>();
		
		findLocation(m, a, b, new ArrayList<Location>(), paths);
		
		int shortestDistance = Integer.MAX_VALUE;
		ArrayList<Location> result = null;
		
		for (int i = 0; i < paths.size(); i++) {
			if (paths.get(i).size() < shortestDistance) {
				shortestDistance = paths.get(i).size();
				result = paths.get(i);
			}
		}
		
		return result;
	}
	
	/** populates paths with directions to the destination */
	public void findLocation(Mall m, Area currentLoc, Location destination,
			ArrayList<Location> visitedLocations, ArrayList<ArrayList<Location>> paths) {
		
		visitedLocations.add(currentLoc);
		
		if (currentLoc.equals(destination)) {
			paths.add(new ArrayList<Location>(visitedLocations));
		}
		
		for (Location h : currentLoc.getHotspots()) {
			if (h.equals(destination)) {
				visitedLocations.add(destination);
				paths.add(new ArrayList<Location>(visitedLocations));
			}
		}
		
		ArrayList<Location> nextLocs = new ArrayList<Location>();
		for (Location l : m.getConnectedAreas(currentLoc)) {
			if (!visitedLocations.contains(l)) {
				nextLocs.add(l);
			}
		}
		
		for (Location l : nextLocs) {
			findLocation(m, (Area) l, destination, new ArrayList<Location>(visitedLocations), paths);
		}
	}
}

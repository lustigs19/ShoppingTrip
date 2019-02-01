package locations;

import java.util.ArrayList;

public class Mall extends Location {
	
	ArrayList<Area> areas;
	int areaIndex = 0;
	ArrayList<Connection> connections;
	
	public static final Mall NATICK_MALL = new Mall("Natick Mall") {{
		Area entrance = new Area("Entrance"),
				hallway = new Area("Hallway"),
				foodCourt = new Area("Food Court");
		
		// the first area added is the starting area
		addArea(entrance, hallway);
		addArea(hallway, entrance, foodCourt);
		addArea(foodCourt, hallway);
	}};

	public Mall(String n) {
		super(n);
		areas = new ArrayList<Area>();
		connections = new ArrayList<Connection>();
	}
	
	public void addArea(Area a, Area... connectedAreas) {
		areas.add(a);
		for (Area cArea : connectedAreas) {
			boolean exists = false;
			for (Connection connection : connections) {
				if (connection.equals(new Connection(a, cArea))) exists = true;
			}
			if (!exists) connections.add(new Connection(a, cArea));
		}
	}
	
	public Area getDefaultArea() {
		return areas.get(0);
	}
	
	public ArrayList<Connection> getConnections(Area a) {
		ArrayList<Connection> tempList = new ArrayList<Connection>();
		for (Connection connection : connections) {
			if (connection.contains(a)) {
				tempList.add(connection);
			}
		}
		// TODO
		return tempList;
	}
}

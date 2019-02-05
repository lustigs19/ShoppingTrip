package locations;

import java.util.ArrayList;

import main.FoodItem;
import main.Item;

public class Mall extends Location {
	
	ArrayList<Area> areas;
	int areaIndex = 0;
	ArrayList<Connection> connections;
	
	public static final Mall DEFAULT_MALL = new Mall("Natick Mall") {{
		Area entrance = new Area("Entrance"),
				hallway = new Area("Hallway"),
				foodCourt = new Area("Food Court");
		
		entrance.addHotspot(new Store("MEMES", new Item("'RIPRIPRIP'", 49.99f),
				new Item("'a bunch of gamers'", 17.49f)));
		
		hallway.addHotspot(new Store("The Lego Store", new Item("Big Ben", 249.99f)));
		
		foodCourt.addHotspot(new Restaurant("Chick-fil-A",
				new FoodItem("Chicken Sandwich", 3.05f, "mmm... tastes so good..."),
				new FoodItem("Deluxe Chicken Sandwich", 3.65f, "omg... so much better than the regular..."),
				new FoodItem("Nuggets 8-pc", 3.05f, "mmm... I sure do love me some chicken nuggets"),
				new FoodItem("Chicken Salad Sandwich", 3.99f, "hmm... it says salad in the name. Must be healthy.")));
		
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
	
	/** adds an area and connecting areas. */
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
		return tempList;
	}
}

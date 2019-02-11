package locations;

import java.util.ArrayList;
import java.util.HashMap;

import main.FoodItem;
import main.Item;

public class Mall extends Location {
	ArrayList<Area> areas;
	int areaIndex = 0;
	ArrayList<Connection> connections;
	
	public static final Mall DEFAULT_MALL = new Mall("Marj Ma-Mall") {{
		Area entrance = new Area("Entrance", true),
				hallway = new Area("Hallway", false),
				foodCourt = new Area("Food Court", false);
				
		
		entrance.addHotspot(new Store("MEMES", new HashMap<Item, Float>() {
			private static final long serialVersionUID = 1L;
		{
				put(new Item("'RIPRIPRIP'"), 49.99f);
				put(new Item("'a bunch of gamers'"), 17.49f);
		}}));
		
		entrance.addHotspot(new Map("Map"));
		
		hallway.addHotspot(new Store("The Lego Store", new HashMap<Item, Float>() {
			private static final long serialVersionUID = 1L;
		{
				put(new Item("Big Ben"), 249.99f);
				put(new FoodItem("Blue 2x2 Lego Block", "Why did I try to eat this? This hurts."), 9.99f);
		}}));
		
		foodCourt.addHotspot(new Restaurant("Chick-fil-A", new HashMap<Item, Float>() {
			private static final long serialVersionUID = 1L;
		{
				put(new FoodItem("Chicken Sandwich", "mmm... tastes so good..."), 3.05f);
				put(new FoodItem("Deluxe Chicken Sandwich", "omg... so much better than the regular..."), 3.65f);
				put(new FoodItem("8-pc Nuggets", "mmm... I sure do love me some chicken nuggets"), 3.05f);
				put(new FoodItem("Chicken Salad Sandwich", "hmm... it says salad in the name. Must be healthy."), 3.99f);
		}}));
		
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
	
	/** returns the starting area for the shopping trip to the mall. */
	public Area getDefaultArea() {
		return (areas.size() > 0 ? areas.get(0) : null);
	}
	
	public ArrayList<Location> getConnectedAreas(Area a) {
		ArrayList<Location> tempList = new ArrayList<Location>();
		for (Connection connection : connections) {
			if (connection.contains(a)) {
				tempList.add(connection.get(a));
			}
		}
		return tempList;
	}
	
	public ArrayList<Area> getAreas() {
		return areas;
	}
	
	/** find location in mall with name n
	 * @param n name of location that you are trying to find
	 */
	public Location getLocation(String n) {
		n = n.toLowerCase();
		
		if (this.getName().toLowerCase().equals(n)) {
			return this;
		}
		
		for (Area a : areas) {
			if (a.getName().toLowerCase().equals(n)) {
				return a;
			}
			
			for (Location h : a.getHotspots()) {
				if (h.getName().toLowerCase().equals(n))
					return h;
			}
		}
		
		return null;
		
	}
}

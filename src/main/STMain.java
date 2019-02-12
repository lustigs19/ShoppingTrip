package main;

import java.util.HashMap;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import locations.Area;
import locations.Hotspot;
import locations.Mall;
import locations.Map;
import locations.Store;

public class STMain {
	
	public static void main(String[] args) {
		System.out.println("What's your name?");
		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();
		
		Float bal = null;
		do {
			System.out.println("Starting balance?");
			String balance = scanner.next();
			
			try {
				bal = Float.parseFloat(balance);
				
				if (bal <= 0) bal = null;
			} catch (NumberFormatException e) {
				
			}
		} while (bal == null);
		
		Mall mall;
		
		do {
			System.out.println("File name to read? Type 'default' for default mall");
			String fileName = scanner.next();
			if (fileName.equals("default")) {
				mall = Mall.DEFAULT_MALL;
			} else {
				try {
					mall = parseJSON(fileName + ".json");
				} catch (Exception e) {
					System.out.println("\nCould not find file.");
					mall = null;
				}
			}
		} while (mall == null);
		
		Shopper shopper = new Shopper(name, bal);
		
		shopper.visit(mall);
		scanner.close();
	}
	
	static Mall parseJSON(String fileName) throws FileNotFoundException, IOException, ParseException {
		// finds the file to parse and makes a JSONObject out of it
		Object parsedObject = new JSONParser().parse(new FileReader(fileName));
		JSONObject jo = (JSONObject) parsedObject;
		
		// creates the mall object
		Mall mall = new Mall((String) jo.get("name"));
		
		// list of areas in JSONArray and array of areas to be added to mall
		JSONArray jAreas = (JSONArray) jo.get("areas");
		Area[] mAreas = new Area[jAreas.size()];
		
		// creates each area in mAreas
		for (int i = 0; i < mAreas.length; i++) {
			JSONObject jArea = (JSONObject)jAreas.get(i);
			mAreas[i] = new Area((String) jArea.get("name"), (Boolean) jArea.get("entrance"));
		}
		
		for (int i = 0; i < mAreas.length; i++) {
			// creates connections between the areas in mCons using jCons
			JSONArray jCons = (JSONArray) ((JSONObject) jAreas.get(i)).get("connections");
			Area[] mCons = new Area[jCons.size()];
			for (int j = 0; j < mCons.length; j++) {
				mCons[j] = findArea(mAreas, (String) jCons.get(j));
			}
			
			// adds all hotspots to the specific area (h = hotspot index)
			JSONArray jHots = (JSONArray) ((JSONObject) jAreas.get(i)).get("hotspots");
			for (int h = 0; h < jHots.size(); h++) {
				JSONObject jHot = (JSONObject) jHots.get(h);
				Hotspot mHot;
				
				if (jHot.get("type").equals("store")) {
					HashMap<Item, Float> items = new HashMap<Item, Float>();
					JSONArray jItems = (JSONArray) jHot.get("items");
					
					// adds all items to the specific store (s = store index)
					for (int s = 0; s < jItems.size(); s++) {
						JSONObject jItem = (JSONObject) jItems.get(s);
						Item mItem;
						
						if ((Boolean) jItem.get("food")) {
							mItem = new FoodItem((String) jItem.get("name"), (String) jItem.get("text"));
						} else {
							mItem = new Item((String) jItem.get("name"));
						}
						
						items.put(mItem, ((Double) jItem.get("cost")).floatValue());
					}
					
					mHot = new Store((String) jHot.get("name"), items);
				} else if (jHot.get("type").equals("map")) {
					mHot = new Map((String) jHot.get("name"));
				} else {
					mHot = null;
				}
				
				mAreas[i].addHotspot(mHot);
			}
			
			// adds the area and connections
			mall.addArea(mAreas[i], mCons);
		}
		
		return mall;
	}
	
	static Area findArea(Area[] areas, String name) {
		for (Area area : areas) {
			if (area.getName().equals(name)) {
				return area;
			}
		}
		
		return null;
	}
}

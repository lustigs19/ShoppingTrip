package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import locations.Store;

public class MallMaker {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		JSONObject jMall = new JSONObject();
		ArrayList<String> areas = new ArrayList<String>();
		
		// create mall with name
		System.out.println("Mall name:");
		String jMallName = sc.nextLine();
		jMall.put("name", jMallName);
		Menu anotherMenu = new Menu("Would you like to add another?", "Yes", "No");
		ArrayList<String[]> mCons = new ArrayList<String[]>();
		
		boolean done = false;
		boolean first = true;
		
		// add area names to array list of strings
		do {
			System.out.println("Area name" + (first ? " (first area is default area):" : ":"));
			areas.add(sc.nextLine());
			switch(anotherMenu.displayAndChoose()) {
			case 1:
				break;
			default:
			case 2:
				done = true;
			}
			first = false;
		} while (!done);
		
		done = false;
		
		if (areas.size() > 1) {
			System.out.println("Choose connections between areas:");
			ConnectionMenu areaMenu = new ConnectionMenu("Areas:", areas.toArray(new String[areas.size()]));
			// add connections between areas
			
			do {
				// print out all areas
				int[] chosen = areaMenu.chooseTwo();
				
				boolean exists = false;
				for (String[] mCon : mCons) {
					if (mCon[0].equals(areas.get(chosen[0])) && mCon[1].equals(areas.get(chosen[1])) ||
							mCon[1].equals(areas.get(chosen[0])) && mCon[0].equals(areas.get(chosen[1]))) {
						exists = true;
					}
				}
				
				if (!exists) mCons.add(new String[] {areas.get(chosen[0]), areas.get(chosen[1])});
				
				anotherMenu.setTitle("Would you like to make another connection?");
				switch(anotherMenu.displayAndChoose()) {
				case 1:
					break;
				default:
				case 2:
					done = true;
				}
			} while (!done);
		}
		
		// deletes duplicate areas
		for (int i = areas.size() - 1; i >= 0; i--) {
			ArrayList<String> tempList = new ArrayList<String>(areas);
			tempList.remove(i);
			
			if (tempList.contains(areas.get(i))) areas.remove(i);
		}
		
		// deletes areas without connections
		if (areas.size() > 1) {
			for (int i = areas.size() - 1; i >= 0; i--) {
				boolean hasConnections = false;
				for (String[] mCon : mCons) {
					if (mCon[0].equals(areas.get(i)) || mCon[1].equals(areas.get(i))) hasConnections = true;
				}
				
				if (!hasConnections) {
					areas.remove(i);
				}
			}
		}
		
		// deletes connections with areas that do not exist anymore
		for (int i = mCons.size() - 1; i >= 0; i--) {
			if (!areas.contains(mCons.get(i)[0]) || !areas.contains(mCons.get(i)[1])) mCons.remove(i);
		}
		
		JSONArray jAreas = new JSONArray();
		JSONArray hotspots = new JSONArray();
		done = false;
		// add hotspots to each area
		for (String a : areas) {
			do {
				JSONObject hotspot = new JSONObject();
				System.out.println("Add a hotspot to area " + a + ":\nHotspot name:");
				String hName = sc.nextLine();
				String type;
				Menu menu = new Menu("Type of hotspot?", "Store", "Map");
				
				// Add hotspots
				switch (menu.displayAndChoose()) {
				default:
				// Add items to store
				case 1:
					type = "store";
					boolean done2 = false;
					boolean done3 = false;
					JSONArray items = new JSONArray();
					
					do {
						boolean hFood;
						String foodText = "";
						String hItem = "";
						JSONObject item = new JSONObject();
						
						System.out.println("Add an item to the store:\nItem name:");
						hItem = sc.nextLine();
						
						Menu foodMenu = new Menu("Food item or not?", "Yes", "No");
						switch(foodMenu.displayAndChoose()) {
						case 1:
							hFood = true;
							do {
								System.out.println("Text after food is eaten:");
								foodText = sc.nextLine();
							} while (foodText.equals(""));
							break;
						default:
						case 2:
							hFood = false;
						}
						
						float iCost = 0;
						
						do {
							System.out.println("Choose a cost:");
							try {
								iCost = Float.parseFloat(sc.nextLine());
								if (iCost > 0) done3 = true;
							} catch (NumberFormatException e) {
								System.out.println("Not a float");
							}
						} while (!done3);
						
						// add item to JSONArray
						item.put("name", hItem);
						item.put("food", hFood);
						if (hFood) item.put("text", foodText);
						item.put("cost", iCost);
						items.add(item);
						
						anotherMenu.setTitle("Would you like to add another item?");
						switch(anotherMenu.displayAndChoose()) {
						case 1:
							break;
						default:
						case 2:
							done2 = true;
						}
						
					} while (!done2);
					
					hotspot.put("items", items);
					break;
				case 2:
					type = "map";
					break;
				}
				
				hotspot.put("name", hName);
				hotspot.put("type", type);
				
				hotspots.add(hotspot);
				
				anotherMenu.setTitle("Would you add another hotspot to area " + a + "?");
				switch(anotherMenu.displayAndChoose()) {
				case 1:
					break;
				default:
				case 2:
					done = true;
					
				}
			} while (!done);
			
			JSONObject jArea = new JSONObject();
			jArea.put("name", a);
			Menu entranceMenu = new Menu("is " + a + " an entrance to the mall?", "Yes", "No");
			if (entranceMenu.displayAndChoose() == 1) {
				jArea.put("entrance", true);
			} else {
				jArea.put("entrance", false);
			}
			
			JSONArray cons = new JSONArray();
			for (String[] mCon : mCons) {
				if (mCon[0].equals(a)) {
					cons.add(mCon[1]);
				} else if (mCon[1].equals(a)) {
					cons.add(mCon[0]);
				}
			}
			jArea.put("hotspots", hotspots);
			jArea.put("connections", cons);
			
			jAreas.add(jArea);
		}
		
		jMall.put("areas", jAreas);
		
		System.out.println("file name?");
		String fileName = sc.nextLine();
		fileName.replaceAll(" ", "");
		
		File file = new File("malls/" + fileName + ".json");
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write(jMall.toJSONString());
		System.out.println("Successfully Copied JSON Object to File malls/" + fileName + ".json");
		System.out.println("\nJSON Object: " + jMall);
		
		writer.close();
		sc.close();
	}

}

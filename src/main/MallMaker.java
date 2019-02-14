package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MallMaker {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		JSONObject jMall = new JSONObject();
		ArrayList<String> areas = new ArrayList<String>();
		
		// create mall with name
		System.out.print("Mall name:");
		String jMallName = sc.next();
		jMall.put("name", jMallName);
		Menu anotherMenu = new Menu("Would you like to add another?", "Yes", "No");
		ArrayList<String[]> mCons = new ArrayList<String[]>();
		
		boolean done = false;
		boolean first = true;
		
		// add area names to array list of strings
		do {
			System.out.println("Area name" + (first ? " (first area is default area):" : ":"));
			areas.add(sc.next());
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
		
		ConnectionMenu areaMenu = new ConnectionMenu("Areas:", (String[]) areas.toArray());
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
			
			switch(anotherMenu.displayAndChoose()) {
			case 1:
				break;
			default:
			case 2:
				done = true;
			}
		} while (!done);
		
		// TODO delete areas without connections
		
		
		done = false;
		// add hotspots to each area
		for (String a : areas) {
			do {
				System.out.println("Add a hotspot to area " + a + ":");
				String hName = sc.next();
				Menu menu = new Menu("Type of hotspot?", "Store", "Map");
				switch (menu.displayAndChoose()) { // TODO
				default:
				case 1:
					break;
				case 2:
					break;
				}
				
				switch(anotherMenu.displayAndChoose()) {
				case 1:
					break;
				default:
				case 2:
					done = true;
				}
			} while (!done);
		}
		
		try (FileWriter file = new FileWriter(jMallName + ".json")) {
			file.write(jMall.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + jMall);
		}
		
		sc.close();
	}

}

package main;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import locations.Mall;

public class STMain {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		System.out.println("What's your name?");
		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();
		
		Float bal = null;
		do {
			System.out.println("Starting balance?");
			String balance = scanner.next();
			
			try {
				bal = Float.parseFloat(balance);
			} catch (NumberFormatException e) {
				
			}
		} while (bal == null);
		
		Mall mall = parseJSON("NatickMall.json");
		Shopper shopper = new Shopper(name, bal);
		
		shopper.visit(mall);
		scanner.close();
	}
	
	static Mall parseJSON(String fileName) throws FileNotFoundException, IOException, ParseException {
		Object parsedObject = new JSONParser().parse(new FileReader(fileName));
		JSONObject jo = (JSONObject) parsedObject;
		
		Mall mall = new Mall((String) jo.get("name"));
		
		JSONArray areas = (JSONArray) jo.get("areas");
		
		for (int i = 0; i < areas.size(); i++) {
			// TODO
		}
		
		return mall;
	}
}

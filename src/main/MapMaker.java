package main;

import java.io.FileWriter;
import java.util.Scanner;

import org.json.simple.JSONObject;

public class MapMaker {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		JSONObject jMall = new JSONObject();
		System.out.print("Mall name:");
		jMall.put("name", sc.next());
		// TODO
		sc.close();
	}

}

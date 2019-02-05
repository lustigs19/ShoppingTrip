package main;

public class Item {
	String name;
	float price;
	
	public Item(String n, float p) {
		name = n;
		price = p;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}
}

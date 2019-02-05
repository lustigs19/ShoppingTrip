package main;

public class FoodItem extends Item {
	
	String text;

	public FoodItem(String n, float p, String s) {
		super(n, p);
		text = s;
	}
	
	public String getText() {
		return text;
	}
}

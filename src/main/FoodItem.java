package main;

public class FoodItem extends Item {
	
	String text;

	/** an item that you eat instead of keeping
	 * @param name of food item
	 * @param string printed after eating */
	public FoodItem(String n, String s) {
		super(n);
		text = s;
	}
	
	public String getText() {
		return text;
	}
}

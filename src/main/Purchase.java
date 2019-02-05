package main;

public class Purchase {
	Item item;
	float cost;
	
	public Purchase(Item i, float c) {
		item = i;
		cost = c;
	}

	public Item getItem() {
		return item;
	}

	public float getCost() {
		return cost;
	}
}

package main;

public class ConnectionMenu extends Menu {

	public ConnectionMenu(String title, String[] args) {
		super(title, args);
	}
	
	public int[] chooseTwo() {
		int i1 = displayAndChoose();
		items.remove(i1);
		int i2 = displayAndChoose();
		if (i2 >= i1) i2++;
		
		return new int[] {i1 - 1, i2 - 1};
	}

}

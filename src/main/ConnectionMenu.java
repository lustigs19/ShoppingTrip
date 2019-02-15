package main;

public class ConnectionMenu extends Menu {

	public ConnectionMenu(String title, String[] args) {
		super(title, args);
	}
	
	public int[] chooseTwo() {
		int i1 = displayAndChoose();
		String s1 = items.remove(i1 - 1);
		int i2 = displayAndChoose();
		if (i2 >= i1) i2++;
		
		items.add(i1 - 1, s1);
		
		return new int[] {i1 - 1, i2 - 1};
	}

}

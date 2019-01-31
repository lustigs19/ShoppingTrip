package locations;

public class Mall extends Location {
	
	Area[] areas;
	
	public static final Mall NATICK_MALL = new Mall("Natick Mall");

	public Mall(String n) {
		super(n);
	}
	
	public void addArea(String n, Area[] connections) {
		
	}
}

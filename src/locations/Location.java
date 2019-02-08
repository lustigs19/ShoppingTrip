package locations;

public abstract class Location {
	String name;
	
	/** names the location
	 * @param n is the name to set the location to 
	 */
	public Location(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals (Object obj) {
		if (this == obj) return true;
		
		if (obj instanceof Location) {
			return ((Location) obj).getName().equals(name);
		}
		
		return false;
	}
}

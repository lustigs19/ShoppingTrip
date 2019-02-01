package locations;

import java.util.HashSet;

public class Connection {
	Area area1;
	Area area2;
	
	public Connection(Area a1, Area a2) {
		area1 = a1;
		area2 = a2;
	}
	
	public Area get(Area a) {
		if (area1.equals(a)) return area2;
		
		if (area2.equals(a)) return area1;
		
		return null;
	}
	
	public boolean contains(Area a) {
		return area1.equals(a) || area2.equals(a);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (obj instanceof Connection) {
			HashSet<Area> tempSet = new HashSet<Area>();
			tempSet.add(area1);
			tempSet.add(area2);
			
			HashSet<Area> objSet = new HashSet<Area>(); 
			objSet.add(((Connection) obj).getAreas()[0]);
			objSet.add(((Connection) obj).getAreas()[1]);
			
			if (tempSet.equals(objSet)) {
				return true;
			}
		}
		
		return false;
	}
	
	public Area[] getAreas() {
		return new Area[] {area1, area2};
	}
}

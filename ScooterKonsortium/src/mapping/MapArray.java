package mapping;

public class MapArray {
	
	protected MapObjekt[][] map;
	protected int width;
	protected int height;
	
	
	public MapArray(int width, int height) {
		this.width = width;
		this.height = height;
		this.map = new MapObjekt[width][height];
		
		clearMap();
	}
	
	public void clearMap() {
		//Leere Map
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				this.map[i][j] = new MapObjekt();
			}
		}
	}
	
	public void placeObject(int x, int y, MapObjekt oMapObj) {
		if(x > 0 && y > 0 && map.length < x && map[0].length < y) {
			System.err.println("Can not place object! The map is to small");
		}
		this.map[x][y] = oMapObj;
	}
	
}

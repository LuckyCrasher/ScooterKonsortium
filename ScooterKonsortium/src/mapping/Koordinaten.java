package mapping;

public class Koordinaten {
	public int x;
	public int y;
	int Array [] = new int[1];
	
	public Koordinaten(int px, int py) {
		this.x = px;
		this.y = py;
	}
	public Koordinaten() {
		this.x = 0;
		this.y = 0;
	}
	
	public void setx(int px) {
		Array[0] = px;
	}
	
	public int getx() {
		return x;
	}
	
	public void sety(int py) {
		Array[1] = py;
	}
	
	public int gety() {
		return y;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("X : %s%n", this.x));
		sb.append(String.format("Y: %s%n", this.y));
		
		return sb.toString();
	}
}

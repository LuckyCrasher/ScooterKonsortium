package konsortiumdata;
import mapping.*;

public class Ladepunkt extends MapObjekt {
	private String sNameLadepunkt = "null";
	private int iLadeCap = 0;
	private int iCurrentUse = 0;
	private String sFirmaOwning;
	
	public Ladepunkt(String psNameLadepunkt, int x, int y, int piLadeCap, int piCurrentUse, String sFirmaOwning) {
		this.x = x;
		this.y = y;
		this.sNameLadepunkt = psNameLadepunkt;
		this.iLadeCap = piLadeCap;
		this.iCurrentUse = piCurrentUse;
		this.sFirmaOwning = sFirmaOwning;
	}
	
	public Ladepunkt() {
		this.sNameLadepunkt = "";
		this.iLadeCap = 0;
		this.iCurrentUse = 0;
	}
	
	public String getNameLadepunkt() {
		return sNameLadepunkt;
	}
	
	public void setNameLadepunkt(String psNameLadepunkt) {
		this.sNameLadepunkt = psNameLadepunkt;
	}
	
	public int getLadeCap() {
		return iLadeCap;
	}
	
	public void setLadeCap(int piLadeCap) {
		this.iLadeCap = piLadeCap;
	}
	
	public int getCurrentUse() {
		return iCurrentUse;
	}
	
	public void setCurrentUse(int piCurrentUse) {
		this.iCurrentUse = piCurrentUse;
	}
	
	public String render() {
		return String.format("L\n%d/%d", this.iCurrentUse, iLadeCap);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("Name: %s%n", this.sNameLadepunkt));
		sb.append(String.format("Costs: %s%n", this.iCurrentUse));
		sb.append(String.format("Address: %s%n", this.iLadeCap));
		sb.append(String.format("Location: %d %d%n", this.x, this.y));
		sb.append(String.format("Name of company: %s%n", this.sFirmaOwning));

		return sb.toString();
	}

	public void setOwnedBy(String sFirmaOwning) {
		this.sFirmaOwning = sFirmaOwning;
	}

	public String getFirmaOwning() {
		return this.sFirmaOwning;
	}
}

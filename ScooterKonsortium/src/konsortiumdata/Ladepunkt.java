package konsortiumdata;
import mapping.*;

public class Ladepunkt extends MapObjekt {
	private String sNameLadepunkt = "null";
	private int iLadeCap = 0;
	private int iCurrentUse = 0;
	
	public Ladepunkt(String psNameLadepunkt, Koordinaten poFixKoord, int piLadeCap, int piCurrentUse) {
		this.sNameLadepunkt = psNameLadepunkt;
		this.iLadeCap = piLadeCap;
		this.iCurrentUse = piCurrentUse;
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
}

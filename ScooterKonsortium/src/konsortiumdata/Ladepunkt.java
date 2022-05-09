package konsortiumdata;

public class Ladepunkt {
	private String sNameLadepunkt;
	private Koordinaten oFixKoord = new Koordinaten();
	private int iLadeCap;
	private int iCurrentUse;
	
	public String getNameLadepunkt() {
		return sNameLadepunkt;
	}
	
	public void setNameLadepunkt(String psNameLadepunkt) {
		this.sNameLadepunkt = psNameLadepunkt;
	}
	
	public Koordinaten getFixKoord() {
		return oFixKoord;
	}
	
	public void setFixKoord(Koordinaten poFixKoord) {
		this.oFixKoord = poFixKoord;
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

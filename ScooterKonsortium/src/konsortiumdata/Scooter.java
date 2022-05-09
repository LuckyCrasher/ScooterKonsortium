package konsortiumdata;

public class Scooter extends MapObjekt {
	private Koordinaten oCurrentKoord = new Koordinaten();
	private int iCurrentProzent;
	private double dCurrentEarn;
	private int iCoveredKm;
	private boolean bCurrentStatus;
	
	public Koordinaten getCurrentKoord() {
		return oCurrentKoord;
	}
	
	public void setCurrentKoord(Koordinaten poCurrentKoord) {
		this.oCurrentKoord = poCurrentKoord;
	}
	
	public int getCurrentPorzent() {
		return iCurrentProzent;
	}
	
	public void setCurrentProzent(int piCurrentProzent) {
		this.iCurrentProzent = piCurrentProzent;
	}
	
	public double getCurrentEarn() {
		return dCurrentEarn;
	}
	
	public void setCurrentEarn(double pdCurrentEarn) {
		this.dCurrentEarn = pdCurrentEarn;
	}
	
	public int getCoveredKm () {
		return iCoveredKm;
	}
	public void setCoveredKm (int piCoveredKm) {
		this.iCoveredKm = piCoveredKm;
	}
	
	public boolean getCurrentStatus() {
		return bCurrentStatus;
	}
	public void setCurrentStatus(boolean pbCurrentStatus) {
		this.bCurrentStatus = pbCurrentStatus;
	}
}
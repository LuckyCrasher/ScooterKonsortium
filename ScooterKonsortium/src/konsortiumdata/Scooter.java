package konsortiumdata;
import mapping.*;

public class Scooter extends MapObjekt {
	private int iCurrentProzent;
	private double dCurrentEarn;
	private int iCoveredKm;
	private boolean bCurrentStatus;
	private String sFirmaOwning;
	
	public Scooter(int piCurrentProzent, double pdCurrentEarn, int piCoveredKm, boolean pbCurrentStatus, String sFirmaOwning) {
		this.iCurrentProzent = piCurrentProzent;
		this.dCurrentEarn = pdCurrentEarn;
		this.iCoveredKm = piCoveredKm;
		this.bCurrentStatus = pbCurrentStatus;
		this.sFirmaOwning = sFirmaOwning;
	}
	
	public Scooter() {
		this.iCurrentProzent = 100;
		this.dCurrentEarn = 0.0;
		this.iCoveredKm = 0;
		this.bCurrentStatus = false;
	}
	
	public int getCurrentProzent() {
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
	
	public String render() {
		return "S";
	}

	public void setOwnedBy(String sFirmaOwning) {
		this.sFirmaOwning = sFirmaOwning;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("Current Status: %s%n", this.bCurrentStatus));
		sb.append(String.format("Current Earn: %s%n", this.dCurrentEarn));
		sb.append(String.format("Covered Kilometers: %skm%n", this.iCoveredKm));
		sb.append(String.format("Current percent: %d%n", this.iCurrentProzent));
		sb.append(String.format("Location: %d %d%n", this.x, this.y));
		sb.append(String.format("Name of company: %s%n", this.sFirmaOwning));

		return sb.toString();
	}

	public String getFirmaOwning() {
		return this.sFirmaOwning;
	}
}

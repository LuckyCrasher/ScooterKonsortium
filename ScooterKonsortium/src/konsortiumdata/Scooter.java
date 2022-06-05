package konsortiumdata;
import mapping.*;
import java.util.UUID;

public class Scooter extends MapObjekt {
	private int iCurrentProzent;
	private double dCurrentEarn;
	private int iCoveredKm;
	private boolean bCurrentStatus;
	private String sFirmaOwning;
	private String sUUID;
	
	public Scooter(int x, int y, int piCurrentProzent, double pdCurrentEarn, int piCoveredKm, boolean pbCurrentStatus, String sFirmaOwning) {
		this.x = x;
		this.y = y;
		this.iCurrentProzent = piCurrentProzent;
		this.dCurrentEarn = pdCurrentEarn;
		this.iCoveredKm = piCoveredKm;
		this.bCurrentStatus = pbCurrentStatus;
		this.sFirmaOwning = sFirmaOwning;
		this.sUUID = generateUUID();
	}
	
	public Scooter(int x, int y, int piCurrentProzent, double pdCurrentEarn, int piCoveredKm, boolean pbCurrentStatus, String sFirmaOwning, String uuid) {
		this.x = x;
		this.y = y;
		this.iCurrentProzent = piCurrentProzent;
		this.dCurrentEarn = pdCurrentEarn;
		this.iCoveredKm = piCoveredKm;
		this.bCurrentStatus = pbCurrentStatus;
		this.sFirmaOwning = sFirmaOwning;
		this.sUUID = uuid;
	}
	
	private String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
	}
	
	public Scooter() {
		this.x = 0;
		this.y = 0;
		this.iCurrentProzent = 100;
		this.dCurrentEarn = 0.0;
		this.iCoveredKm = 0;
		this.bCurrentStatus = false;
		this.sUUID = this.generateUUID();
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
	
	public String getUUID() {
		return this.sUUID;
	}
	
	public void setCurrentStatus(boolean pbCurrentStatus) {
		this.bCurrentStatus = pbCurrentStatus;
	}
	
	public String render() {
		return String.format("S\n% d%%", iCurrentProzent);
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

package scooterkonsortium;

public class Firma {
	private String sNameFirma;
	private double KostenJeFahrt;
	private String sAdresse;
	private int iPLZ;
	private String sStadt;
	private long lHotline;

	public String getName() {
		return sNameFirma;
	}

	public void setName(String psNameFirma) {
		this.sNameFirma = psNameFirma;
	}

	// Eine setter und getter Methode f�r KostenJeFahrt ist nicht n�tig, da diese
	// laut Aufgabe Fix sind.
	// Kann aber im Laufe des Projektes noch erg�nzt werden,
	// wenn man sich dazu entscheidet eine M�glichkeit anzubieten im Setup Mode den
	// Preis zu setzen.

	public String getAdresse() {
		return sAdresse;
	}
	
	public void setAdresse(String psAdresse) {
		this.sAdresse = psAdresse; 
	}
	
	public int getPLZ() {
		return iPLZ;
	}
	
	public void setPLZ(int piPLZ) {
		this.iPLZ = piPLZ;
	}
	
	public String getStadt() {
		return sStadt;
	}
	
	public void setStadt(String psStadt) {
		this.sStadt = psStadt;
	}
	
	public long getHotline() {
		return lHotline;
	}
	
	public void setHotline(long plHotline) {
		this.lHotline = plHotline;
	}
}

package konsortiumdata;

public class Firma {
	private String sNameFirma = "null";
	private double KostenJeFahrt = 0.0;
	private String sAdresse = "null";
	private int iPLZ = 0;
	private String sStadt = "null";
	private String sHotline = "0";
	
	public Firma (String psNameFirma, double pdKostenJeFahrt, String psAdresse, int piPLZ, String psStadt, String psHotline) {
		this.sNameFirma = psNameFirma;
		this.KostenJeFahrt = pdKostenJeFahrt;
		this.sAdresse = psAdresse; 
		this.iPLZ = piPLZ;
		this.sStadt = psStadt;
		this.sHotline = psHotline;
	}
	

	public String getName() {
		return sNameFirma;
	}

	public void setName(String psNameFirma) {
		this.sNameFirma = psNameFirma;
	}

	// Eine setter und getter Methode für KostenJeFahrt ist nicht nötig, da diese
	// laut Aufgabe Fix sind.
	// Kann aber im Laufe des Projektes noch ergänzt werden,
	// wenn man sich dazu entscheidet eine Möglichkeit anzubieten im Setup Mode den
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

	public String getHotline() {
		return sHotline;
	}

	public void setHotline(String psHotline) {
		this.sHotline = psHotline;
	}
}

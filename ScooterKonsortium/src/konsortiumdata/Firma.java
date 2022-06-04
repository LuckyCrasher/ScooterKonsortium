package konsortiumdata;

import java.util.ArrayList;

public class Firma {
	private String sNameFirma = "null";
	private double dKostenJeFahrt = 0.0;
	private String sAdresse = "null";
	private int iPLZ = 0;
	private String sStadt = "null";
	private String sHotline = "0";
	
	private ArrayList<Scooter> aoScooter = new ArrayList<>();
	private ArrayList<Ladepunkt> aoLadepunkte = new ArrayList<>();
	
	public Firma (String psNameFirma, double pdKostenJeFahrt, String psAdresse, int piPLZ, String psStadt, String psHotline) {
		this.sNameFirma = psNameFirma;
		this.dKostenJeFahrt = pdKostenJeFahrt;
		this.sAdresse = psAdresse; 
		this.iPLZ = piPLZ;
		this.sStadt = psStadt;
		this.sHotline = psHotline;
	}
	
	public Firma () {
		// Much cleaner to init values here
		// Only needed by manual creation through user...
		this.sNameFirma = "";
		this.dKostenJeFahrt = 0.0;
		this.sAdresse = "";
		this.iPLZ = 0;
		this.sStadt = "";
		this.sHotline = "";
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

	public double getKostenJeFahrt()  {
		return this.dKostenJeFahrt;
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
	
	/**
	 * Adds a scooter to the Companies list of scooters
	 * @param oScooter
	 */
	public void putScooter(Scooter oScooter) {
		this.aoScooter.add(oScooter);
	}
	
	/**
	 * Adds a Ladepunkt to the Companies list of Ladepunkte
	 * @param oLadepunkt
	 */
	public void putLadepunkt(Ladepunkt oLadepunkt) {
		this.aoLadepunkte.add(oLadepunkt);
	}
	
	public Scooter[] getScooters() {
		Scooter out[] = new Scooter[this.aoScooter.size()];
		return this.aoScooter.toArray(out);
	}
	
	public void deleteScooter (int x, int y) {
		if(!aoScooter.removeIf((s)-> s.getx()== x && s.gety()== y)){
			System.err.println("The Scooter cannot be deleted as there is no Scooter on this coordinate");
		}
	}
	public Ladepunkt[] getladepunkte() {
		Ladepunkt out[] = new Ladepunkt[this.aoLadepunkte.size()];
		return this.aoLadepunkte.toArray(out);
	}
	
	public void deleteLadepunkt(String pNameLadepunkt) {
		if(!aoLadepunkte.removeIf((l) -> l.getNameLadepunkt().equals(pNameLadepunkt))) {
			System.err.println("The Ladepunkt cannot be deleted as the Ladepunkt does not exist");
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("Name: %s%n", this.sNameFirma));
		sb.append(String.format("Costs: %s%n", this.dKostenJeFahrt));
		sb.append(String.format("Address: %s%n", this.sAdresse));
		sb.append(String.format("Post Code: %s%n", this.iPLZ));
		sb.append(String.format("City: %s%n", this.sStadt));
		sb.append(String.format("Hotline: %s%n", this.sHotline));

		return sb.toString();
	}
}

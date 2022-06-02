package konsortiumdata;

import java.util.ArrayList;
import java.util.HashMap;

public class KonsortiumData {
	
	/*
	 * HashMap saves companies so that no company can exist twice
	 * Layout is:
	 * 	NameOfCompany: String -> Firma object: Firma
	 * 
	 */
	private HashMap<String, Firma> oFirmen = new HashMap<>();
	
	
	/**
	 * Pushes all the data to the database
	 */
	public void pushAllData() {
		
	}

	/**
	 * Pushes all Companies to the database
	 */
	public void pushFirmen() {
		
	}
	
	/**
	 * Pushes specified Firma to database
	 * 
	 * @param sNameFirma
	 */
	public void PushFirma(String sNameFirma) {
		
	}
	
	/**
	 * Pushes all scooters of specified company to database
	 * @param sNameFirma
	 */
	public void pushScooter(String sNameFirma) {
		
	}
	
	
	/**
	 * Pushes all Ladepunkte of specified company to database
	 * @param sNameFirma
	 */
	public void pushLadepunkt(String sNameFirma) {
		
	}
	
	/**
	 * Adds a new Firma to the internal Data Structure 
	 * @param oFirma
	 */
	public void addFirma(Firma oFirma) {
		this.oFirmen.put(oFirma.getName(), oFirma);
	}
	
	public void deleteFirma(String sNameFirma) {
		if (this.oFirmen.remove(sNameFirma) == null) {
			System.err.printf("The Company %s cannot be deleted as the company does not exist%n", sNameFirma);
		}
		
	}
	
	public void deleteLadepunkt(String Ladepunkt) {
	
	}
	
	public void deleteScooter() {
		
	}
	/**
	 * Adds a Scooter to the specified Company
	 * @param sCompanyName
	 * @param oScooter
	 */
	public void addScooter(String sCompanyName, Scooter oScooter) {
		if(!this.oFirmen.containsKey(sCompanyName)) {
			System.err.println("The scooter cannot be added to the company as the company does not exist");
			return;
		}
		this.oFirmen.get(sCompanyName).putScooter(oScooter);
		
	}
	
	/**
	 * Adds a Ladepunkt to the specified Company
	 * @param sCompanyName
	 * @param oLadepunkt
	 */
	public void addladepunkt(String sCompanyName, Ladepunkt oLadepunkt) {
		if(!this.oFirmen.containsKey(sCompanyName)) {
			System.err.println("The Ladepunkt cannot be added to the company as the company does not exist");
			return;
		}
		this.oFirmen.get(sCompanyName).putLadepunkt(oLadepunkt);
	}
	
	public String[] getFirmaNames() {
		String out[] = new String[oFirmen.size()];
		return this.oFirmen.keySet().toArray(out);
	}
	
	/**
	 * retrieves the Firma from the array list
	 * !! Uses the internal Data Structure 
	 * @param sNameFrima
	 * @return
	 */
	public Firma getFirma(String sNameFrima) {
		return this.oFirmen.get(sNameFrima);
		
	}
	
	/**
	 * retrieves array of ladepunkte from the specified Firma
	 * !! Uses the internal Data Structure 
	 * @param sNameFrima
	 * @return
	 */
	public Ladepunkt[] getLadepunkte(String sNameFrima) {
		return null;
		
	}
	
	/**
	 * retrieves array of scooters from the specified Firma
	 * !! Uses the internal Data Structure 
	 * @param sNameFirma
	 * @return
	 */
	public Scooter[] getScooter(String sNameFirma) {
		return null;
	}

	public boolean containsCompany(String sFirmenName) {
		return this.oFirmen.containsKey(sFirmenName);
	}
	
}

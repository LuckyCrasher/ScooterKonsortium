package database;

import java.sql.SQLException;

import konsortiumdata.*;

public class Datenbank {
	private DatenbankVerbindung oDBConnection;
	
	
	public Datenbank() {
		this.oDBConnection = new DatenbankVerbindung();
		this.oDBConnection.connect();
	}
	
	public void load() {
		oDBConnection.connect();
	}
	
	public void putScooter(Scooter poScooter) throws SQLException {
		int koordX = poScooter.getx();
		int koordY = poScooter.gety();
		int ladezustand = poScooter.getCurrentProzent();
		int strecke = poScooter.getCoveredKm();
		boolean fahrtzustand = poScooter.getCurrentStatus();
		double einnahmen = poScooter.getCurrentEarn();
		String name = poScooter.getFirmaOwning();
		int firmenID = getFirmaID(name);
		
		String sQuerry = "INSERT INTO scooter_daten (aufenthalt_x, aufenthalt_y, ladezustand, zurueckgelegte_strecke, fahrtzustand_aktuell,"
				+ " einnahmen_aktuell, firmenID) VALUES (" + koordX + ", " + koordY + ", " + ladezustand + ", " + strecke + ", " + fahrtzustand + ", " + einnahmen + ", " + firmenID + ");";
		
		oDBConnection.sendQuerryNoReturn(sQuerry);
	}
	
	public int getFirmaID(String name) throws SQLException {
		String sQuerry = "SELECT ID FROM firma_daten WHERE name LIKE \"" + name + "\";";
		String[] returnValue = oDBConnection.sendQuerry(sQuerry);
		String returnValue2 = returnValue[0];
		int i = Integer.parseInt(returnValue2);
		return i;
	}
	
	public void putFirma(Firma poFirma) throws SQLException {
		String name = poFirma.getName();
		String adresse = poFirma.getAdresse();
		String hotline = poFirma.getHotline();
		
		int PLZ = poFirma.getPLZ();
		String stadt = poFirma.getStadt();
		String sQuerry = "INSERT IGNORE INTO ort (stadt, PLZ) VALUES (\"" + stadt + "\", \"" + PLZ + "\");";
		putOrt(sQuerry);
		
		int ortsID = getOrt(PLZ);
		
		String sQuerry2 = "INSERT INTO firma_daten (name, adresse, hotline, ortsID) VALUES (\"" + name + "\", \"" + adresse + "\", \"" + hotline + "\", " + ortsID + ");";
		oDBConnection.sendQuerryNoReturn(sQuerry2);
	}
	
	public void putOrt(String sQuerry) throws SQLException {
		oDBConnection.sendQuerryNoReturn(sQuerry);
	}
	
	public int getOrt(int PLZ) throws SQLException {
		String sQuerry = "SELECT ortsID FROM ort WHERE PLZ = " + PLZ + ";";
		String[] returnValue = oDBConnection.sendQuerry(sQuerry);
		String returnValue2 = returnValue[0];
		int i = Integer.parseInt(returnValue2);
		return i;
	}
	
	public void putLadepunkt(Ladepunkt poLadepunkt) throws SQLException {
		String name = poLadepunkt.getNameLadepunkt();
		int max_kapazitaet = poLadepunkt.getLadeCap();
		int aktuell_benutzt = poLadepunkt.getCurrentUse();
		int fixe_koords_x = poLadepunkt.getx();
		int fixe_koords_y = poLadepunkt.gety();
		
		String firma = poLadepunkt.getFirmaOwning();
		int firmenID = getFirmaID(firma);
		
		String sQuerry = "INSERT INTO ladepunkt_daten (name, max_kapazitaet, aktuell_benutzt, fixe_koords_x, fixe_koords_y, firmenID) VALUES (\"" + name + "\", " + max_kapazitaet + ", " + aktuell_benutzt + ", " + fixe_koords_x + ", " + fixe_koords_y + ", " + firmenID + ");";
		oDBConnection.sendQuerryNoReturn(sQuerry);
	}
}

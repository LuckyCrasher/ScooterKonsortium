package database;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;

import konsortiumdata.*;

public class Datenbank {
	private DatenbankVerbindung oDBConnection;
	
	public Datenbank() {
		this.oDBConnection  = new DatenbankVerbindung();
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
		String sQuerry = "SELECT ID FROM firma_daten WHERE name LIKE '" + name + "';";
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
		String sQuerry = "INSERT INTO ort (stadt, PLZ) VALUES (\"" + stadt + "\", \"" + PLZ + "\");";
		putStadt(sQuerry);
		
		int ortsID = getOrt(PLZ);
		
		String sQuerry2 = "INSERT INTO firma_daten (name, adresse, hotline, ortsID) VALUES (\"" + name + "\", \"" + adresse + "\", \"" + hotline + "\", " + ortsID + ");";
		oDBConnection.sendQuerryNoReturn(sQuerry2);
	}
	
	public void putStadt(String sQuerry) throws SQLException {
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

	public void putEverything(Firma firma) throws SQLException {
		this.putFirma(firma);
		for(Scooter s : firma.getScooters()) {
			this.putScooter(s);
		}
		for(Ladepunkt l: firma.getladepunkte()) {
			this.putLadepunkt(l);
		}
	}
	
	
	public void updateAll(Firma poFirma) throws SQLException {
		//Firma
		String fname = poFirma.getName();
		String adresse = poFirma.getAdresse();
		String hotline = poFirma.getHotline();
		int PLZ = poFirma.getPLZ();
		
		int ortsID = getOrt(PLZ);
		
		String sQuerry2 = String.format("UPDATE firma_daten SET name='%s', adresse='%s', hotline='%s', ortsID=%d WHERE name LIKE '%s';",
				fname, adresse, hotline, ortsID, fname);
		oDBConnection.sendQuerryNoReturn(sQuerry2);
		
		for(Scooter s : poFirma.getScooters()) {
			updateScooter(s);
		}
		
		for(Ladepunkt l : poFirma.getladepunkte()) {
			updateLadepunkt(l);
		}
		
	}
	
	public void updateFirma(Firma oFirma) throws SQLException {
		String fname = oFirma.getName();
		String adresse = oFirma.getAdresse();
		String hotline = oFirma.getHotline();
		int PLZ = oFirma.getPLZ();
		
		int ortsID = getOrt(PLZ);
		
		String sQuerry2 = String.format("UPDATE firma_daten SET name='%s', adresse='%s', hotline='%s', ortsID=%d WHERE name LIKE '%s';",
				fname, adresse, hotline, ortsID, fname);
		oDBConnection.sendQuerryNoReturn(sQuerry2);
	}
	
	public void updateScooter(Scooter poScooter) throws SQLException {
		int koordX = poScooter.getx();
		int koordY = poScooter.gety();
		int ladezustand = poScooter.getCurrentProzent();
		int strecke = poScooter.getCoveredKm();
		boolean fahrtzustand = poScooter.getCurrentStatus();
		double einnahmen = poScooter.getCurrentEarn();
		String name = poScooter.getFirmaOwning();
		int firmenID = getFirmaID(name);
		
		String sQuerry = String.format(Locale.US, "UPDATE scooter_daten SET aufenthalt_x=%d, aufenthalt_y=%d, ladezustand=%d, zurueckgelegte_strecke=%d,"
				+ "fahrtzustand_aktuell=%b, einnahmen_aktuell=%f, firmenID=%d  WHERE aufenthalt_x=%d AND aufenthalt_y=%d;",
				koordX, koordY, ladezustand, strecke, fahrtzustand, einnahmen, firmenID, koordX, koordY);
		oDBConnection.sendQuerryNoReturn(sQuerry);
	}
	
	public void updateLadepunkt(Ladepunkt poLadepunkt) throws SQLException {
		String name = poLadepunkt.getNameLadepunkt();
		int max_kapazitaet = poLadepunkt.getLadeCap();
		int aktuell_benutzt = poLadepunkt.getCurrentUse();
		int fixe_koords_x = poLadepunkt.getx();
		int fixe_koords_y = poLadepunkt.gety();
		
		String firma = poLadepunkt.getFirmaOwning();
		int firmenID = getFirmaID(firma);
		
		String sQuerry = String.format("INSERT INTO ladepunkt_daten (ID, name, max_kapazitaet, aktuell_benutzt, fixe_koords_x, fixe_koords_y, firmenID) "
				+ "VALUES (%d, %s, %d, %d, %d, %d) ON DUPLICATE KEY UPDATE "
				+ "name = VALUES(name), max_kapazitaet = VALUES(max_kapazitaet), aktuell_benutzt=VALUES(aktuell_benutzt), fixe_koords_x=VALUES(fixe_koords_x), fixe_koords_y=VALUES(fixe_koords_y);",
				firmenID, name, max_kapazitaet, aktuell_benutzt, fixe_koords_x, fixe_koords_y);
		/*
		INSERT INTO MyTable (quote_id, order_id, other_data) VALUES ('q200', 'o100', 'blah blah')
		ON DUPLICATE KEY UPDATE 
		  other_data = VALUES(other_data);
		  */
		oDBConnection.sendQuerryNoReturn(sQuerry);
	}
	
	public void pushFirmen(Firma oFirma) throws SQLException {
		String nameFirma = oFirma.getName();
		String sQuerry = "SELECT ID FROM firma_daten WHERE name LIKE \"" + nameFirma +"\";";
		String[] results = new String[100];
		results = oDBConnection.sendQuerry(sQuerry);
		System.out.println(Arrays.toString(results));
		
		if (results[0] == null) {
			this.putFirma(oFirma);
		} else {
			this.updateFirma(oFirma);
		}
	}
	

}

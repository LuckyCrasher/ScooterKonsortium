package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	
	public int pushStadt(String sStadt, int PLZ) throws SQLException {
		String sQuerry = String.format("SELECT ortsID FROM ort WHERE PLZ = %d;", PLZ);
		String[] results = oDBConnection.sendQuerry(sQuerry);
		System.out.println(Arrays.toString(results));
		
		if (results[0] == null) {
			return this.putStadt(sStadt, PLZ);
		}
		return Integer.parseInt(results[0]);
	}
	
	public int putStadt(String sStadt, int PLZ) throws SQLException {
		String sQuerry = String.format("INSERT INTO ort (stadt, PLZ) VALUES ('%s', '%d')", sStadt, PLZ);
		oDBConnection.sendQuerryNoReturn(sQuerry);
		int id = Integer.parseInt(oDBConnection.sendQuerry("SELECT LAST_INSERT_ID();")[0]);
		
		return id;
	}
	
	public int getOrt(int PLZ) throws SQLException {
		String sQuerry = "SELECT ortsID FROM ort WHERE PLZ = " + PLZ + ";";
		String[] returnValue = oDBConnection.sendQuerry(sQuerry);
		String returnValue2 = returnValue[0];
		int i;
		if (returnValue2 != null) {
			i = Integer.parseInt(returnValue2);
		} else {
			i=-1;
		}
		return i;
	}

	public void pushEverything(Firma firma) throws SQLException {
		this.pushFirma(firma);
		for(Scooter s : firma.getScooters()) {
			this.pushScooter(s);
		}
		for(Ladepunkt l: firma.getladepunkte()) {
			this.pushLadepunkt(l);
		}
	}
	
	public void pushFirma(Firma oFirma) throws SQLException {
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

	public int getFirmaID(String name) throws SQLException {
		String sQuerry = String.format("SELECT ID FROM firma_daten WHERE name LIKE '%s';", name);
		String[] returnValue = oDBConnection.sendQuerry(sQuerry);
		String returnValue2 = returnValue[0];
		int i = Integer.parseInt(returnValue2);
		return i;
	}
	
	public void putFirma(Firma poFirma) throws SQLException {
		String name = poFirma.getName();
		String adresse = poFirma.getAdresse();
		String hotline = poFirma.getHotline();
		
		int ortsID = this.pushStadt(poFirma.getStadt(), poFirma.getPLZ());
		
		String sQuerry2 = String.format("INSERT INTO firma_daten (name, adresse, hotline, ortsID)"
				+ "VALUES ('%s', '%s', '%s', '%d')", name, adresse, hotline, ortsID);
		oDBConnection.sendQuerryNoReturn(sQuerry2);
	}

	public void updateFirma(Firma poFirma) throws SQLException {
		//Firma
		String fname = poFirma.getName();
		String adresse = poFirma.getAdresse();
		String hotline = poFirma.getHotline();
		
		int ortsID = pushStadt(poFirma.getStadt(), poFirma.getPLZ());
		
		String sQuerry2 = String.format("UPDATE firma_daten SET name='%s', adresse='%s', hotline='%s', ortsID=%d WHERE name LIKE '%s';",
				fname, adresse, hotline, ortsID, fname);
		oDBConnection.sendQuerryNoReturn(sQuerry2);
	}
	
	public Firma[] fetchAll() throws SQLException {
		String sQuerry = "SELECT * FROM Firmen";
		Statement stmt = oDBConnection.createStatement();
		ResultSet rs = stmt.executeQuery(sQuerry);
		ArrayList<Firma> alFirmen = new ArrayList<>();
		Firma f;
		String name;
        double costs;
        String adresse;
        String hotline;
        String stadt;
        int plz;
		while (rs.next())
	    {
			int id = rs.getInt("id");
	        name    = rs.getString("name");
	        costs   = rs.getDouble("kosten_per_km");
	        adresse = rs.getString("adresse");
	        hotline = rs.getString("hotline");
	        stadt   = rs.getString("stadt");
	        plz        = rs.getInt("PLZ");
	        f = new Firma(name, costs, adresse, plz, stadt, hotline);
	        for(Ladepunkt l : this.fetchLadepunkt(name)) {
	        	f.putLadepunkt(l);
	        }
	        for(Scooter s : this.fetchScooters(name)) {
	        	f.putScooter(s);
	        }
	        alFirmen.add(f);
	    }
		stmt.close();
		
		Firma[] out = new Firma[alFirmen.size()];
		return alFirmen.toArray(out);
	}
	


	private void pushLadepunkt(Ladepunkt l) throws SQLException {
		String sQuerry = String.format("SELECT name FROM ladepunkt_daten WHERE name like '%s';", l.getNameLadepunkt());
		String[] results = oDBConnection.sendQuerry(sQuerry);
		System.out.println(Arrays.toString(results));
		
		if (results[0] == null) {
			this.putLadepunkt(l);
		} else {
			this.updateLadepunkt(l);
		}
	}
	
	public void updateLadepunkt(Ladepunkt poLadepunkt) throws SQLException {
		String name = poLadepunkt.getNameLadepunkt();
		int max_kapazitaet = poLadepunkt.getLadeCap();
		int aktuell_benutzt = poLadepunkt.getCurrentUse();
		int fixe_koords_x = poLadepunkt.getx();
		int fixe_koords_y = poLadepunkt.gety();
		
		String firma = poLadepunkt.getFirmaOwning();
		int firmenID = getFirmaID(firma);
		
		String sQuerry = String.format("UPDATE ladepunkt_daten SET name='%s', max_kapazitaet=%d, aktuell_benutzt=%d, fixe_koords_x=%d, fixe_koords_y=%d WHERE name like '%s'",
				name, max_kapazitaet, aktuell_benutzt, fixe_koords_x, fixe_koords_y, name);
		
		oDBConnection.sendQuerryNoReturn(sQuerry);
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
	
	public Ladepunkt[] fetchLadepunkt(String sNameFirma) throws SQLException {
		String sQuerry = String.format("SELECT * FROM Ladepunkte WHERE Firma LIKE '%s';", sNameFirma);
		Statement stmt = oDBConnection.createStatement();
		ResultSet rs = stmt.executeQuery(sQuerry);
		ArrayList<Ladepunkt> alLadepunkte = new ArrayList<>();
		Ladepunkt l;
		String name;
        int    max_cap;
        int    cur_cap;
        int    x;
        int    y;
        String nameFirma;
		while (rs.next())
	    {
			int id    = rs.getInt("id");
	        name      = rs.getString("LadepunktName");
	        max_cap   = rs.getInt("max_kapazitaet");
	        cur_cap   = rs.getInt("aktuell_benutzt");
	        x         = rs.getInt("fixe_koords_x");
	        y         = rs.getInt("fixe_koords_y");
	        nameFirma = rs.getString("FirmaName");
	        l = new Ladepunkt(name, x, y, max_cap, cur_cap, nameFirma);
	        alLadepunkte.add(l);
	    }
		stmt.close();
		
		Ladepunkt[] out = new Ladepunkt[alLadepunkte.size()];
		return alLadepunkte.toArray(out);
	}
	
	private void pushScooter(Scooter s) throws SQLException {
		String sQuerry = String.format("SELECT ID FROM scooter_daten WHERE uuid like '%s';", s.getUUID());
		String[] results = oDBConnection.sendQuerry(sQuerry);
		System.out.println(Arrays.toString(results));
		
		if (results[0] == null) {
			this.putScooter(s);
		} else {
			this.updateScooter(s);
		}
		
	}
	
	public void putScooter(Scooter poScooter) throws SQLException {
		int koordX = poScooter.getx();
		int koordY = poScooter.gety();
		int ladezustand = poScooter.getCurrentProzent();
		int strecke = poScooter.getCoveredKm();
		boolean fahrtzustand = poScooter.getCurrentStatus();
		double einnahmen = poScooter.getCurrentEarn();
		String name = poScooter.getFirmaOwning();
		String uuid = poScooter.getUUID();
		int firmenID = getFirmaID(name);
		
		String sQuerry = String.format(Locale.US, "INSERT INTO scooter_daten (aufenthalt_x, aufenthalt_y, ladezustand, zurueckgelegte_strecke, fahrtzustand_aktuell,"
				+ " einnahmen_aktuell, firmenID, uuid) VALUES (%d, %d, %d, %d, %b, %f, %d,'%s');", koordX, koordY, ladezustand, strecke, fahrtzustand, einnahmen, firmenID, uuid);
		
		oDBConnection.sendQuerryNoReturn(sQuerry);
	}
	
	public void updateScooter(Scooter poScooter) throws SQLException {
		int koordX = poScooter.getx();
		int koordY = poScooter.gety();
		int ladezustand = poScooter.getCurrentProzent();
		int strecke = poScooter.getCoveredKm();
		boolean fahrtzustand = poScooter.getCurrentStatus();
		double einnahmen = poScooter.getCurrentEarn();
		String name = poScooter.getFirmaOwning();
		String uuid = poScooter.getUUID();
		int firmenID = getFirmaID(name);
		
		String sQuerry = String.format(Locale.US, "UPDATE scooter_daten SET aufenthalt_x=%d, aufenthalt_y=%d, ladezustand=%d, zurueckgelegte_strecke=%d,"
				+ "fahrtzustand_aktuell=%b, einnahmen_aktuell=%f, firmenID=%d  WHERE uuid LIKE '%s'",
				koordX, koordY, ladezustand, strecke, fahrtzustand, einnahmen, firmenID, uuid);
		oDBConnection.sendQuerryNoReturn(sQuerry);
	}
	
	private Scooter[] fetchScooters(String sNameFirma) throws SQLException {
		String sQuerry = String.format("SELECT * FROM Ladepunkte WHERE Firma LIKE '%s';", sNameFirma);
		Statement stmt = oDBConnection.createStatement();
		ResultSet rs = stmt.executeQuery(sQuerry);
		ArrayList<Scooter> alScooter = new ArrayList<>();
		Scooter s;
        
        int     x;
        int     y;
        int     charge;
        int     milage;
        boolean isCharging;
        double  current_earn;
        String  nameOfCompany;
		while (rs.next())
	    {
			int id        = rs.getInt("id");
	        x             = rs.getInt("LadepunktName");
	        y             = rs.getInt("max_kapazitaet");
	        charge        = rs.getInt("aktuell_benutzt");
	        milage        = rs.getInt("fixe_koords_x");
	        isCharging    = rs.getBoolean("fixe_koords_y");
	        nameOfCompany = rs.getString("LadepunktName");
	        current_earn  = rs.getDouble("FirmaName");
	        s = new Scooter(x, y, charge, current_earn, milage, isCharging, nameOfCompany);
	        alScooter.add(s);
	    }
		stmt.close();
		
		Scooter[] out = new Scooter[alScooter.size()];
		return alScooter.toArray(out);
	}

	public void setServer(String sServer) {
		this.oDBConnection.setServer(sServer);
	}
	
	public void setPort(int iPort) {
		this.oDBConnection.setPort(iPort);
	}
	
	public void setDatabase(String sDatabase) {
		this.oDBConnection.setDatabase(sDatabase);
	}
	
	public void setUser(String sUser) {
		this.oDBConnection.setUser(sUser);
	}
	
	public void setPassword(String sPassword) {
		this.oDBConnection.setPassword(sPassword);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("Server: %s%n", this.oDBConnection.getServer()));
		sb.append(String.format("Port: %d%n", this.oDBConnection.getPort()));
		sb.append(String.format("Database: %s%n", this.oDBConnection.getDatabase()));
		sb.append(String.format("User: %s%n", this.oDBConnection.getUser()));

		return sb.toString();
	}

	public boolean connect() {
		return this.oDBConnection.connect();
	}
	
}

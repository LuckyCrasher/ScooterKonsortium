package scooterkonsortium;
import java.sql.SQLException;
import java.util.Arrays;

import database.*;
import konsortiumdata.Firma;
import konsortiumdata.KonsortiumData;
import konsortiumdata.Ladepunkt;
import konsortiumdata.Scooter;
import mapping.Koordinaten;

public class Main {
	
	private static UserInterface oUserInterface;
	private static Datenbank oDatenbank;
	private static KonsortiumData oData;
	
	
	public static void main(String[] args) {
		oDatenbank = new Datenbank();
		oData = new KonsortiumData(oDatenbank);
		
		setFakeData();
		//testDatabase();
		
		oUserInterface = new UserInterface(oData, oDatenbank);
		oUserInterface.mainLoop();
	}
	
	/*
	private static void testDatabase() {
		//oData.pushAllData();
		try {
			for(Firma f : oDatenbank.fetchFirmen()) {
				System.out.println(f);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	private static void setFakeData() {
		Firma f = new Firma("Firma1", 0.55, "test Stra�e 22", 42279, "Wuppertal", "+49 15522 187654");
		Scooter s = new Scooter(7,7,40, 0.1, 5, false, "Firma1", "249298b8-126d-4b2f-ad73-e86da6d510a8");
		f.putScooter(s);
		Ladepunkt l = new Ladepunkt("Lade1",0 ,0, 10, 5, f.getName());
		f.putLadepunkt(l);
		l = new Ladepunkt("Lade2",9 ,0, 10, 7, f.getName());
		f.putLadepunkt(l);
		l = new Ladepunkt("Lade3",0 ,9, 8, 5, f.getName());
		f.putLadepunkt(l);
		oData.addFirma(f);
		

		f = new Firma("Firma2", 0, " ", 0, " ", " ");
		s = new Scooter(4,6,50, 0.7, 5, false, "Firma2", "a03222b4-29ff-4c54-8895-d25bf0513f98");
		f.putScooter(s);
		l = new Ladepunkt("Lade4",9 ,9, 4, 2, f.getName());
		f.putLadepunkt(l);
		l = new Ladepunkt("Lade5",5 ,5, 10, 5, f.getName());
		f.putLadepunkt(l);
		oData.addFirma(f);
		
	}

}

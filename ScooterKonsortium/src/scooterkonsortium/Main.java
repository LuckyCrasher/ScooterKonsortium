package scooterkonsortium;
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
		oData = new KonsortiumData();
		
		setFakeData();
		
		oUserInterface = new UserInterface(oData);
		oDatenbank = new Datenbank();
		oUserInterface.mainLoop();
	}
	
	private static void setFakeData() {
		Firma f = new Firma("Firma1", 0, "TEST", 0, "TEST", "TEST");
		Scooter s = new Scooter(3,3,20, 0.3, 5, false, "Firma1");
		f.putScooter(s);
		Ladepunkt l = new Ladepunkt("Lade1",0 ,0, 10, 5, f.getName());
		f.putLadepunkt(l);
		l = new Ladepunkt("Lade2",9 ,0, 10, 5, f.getName());
		f.putLadepunkt(l);
		l = new Ladepunkt("Lade3",0 ,9, 10, 5, f.getName());
		f.putLadepunkt(l);
		oData.addFirma(f);
		
		f = new Firma("Firma2", 0, " ", 0, " ", " ");
		s = new Scooter(4,6,90, 0.3, 5, false, "Firma2");
		f.putScooter(s);
		l = new Ladepunkt("Lade4",9 ,9, 10, 5, f.getName());
		f.putLadepunkt(l);
		l = new Ladepunkt("Lade5",5 ,5, 10, 5, f.getName());
		f.putLadepunkt(l);
		oData.addFirma(f);
		
	}

	public static void mainLoop() {
		while (true) {
			oUserInterface.showMenu();
		}
	}

}

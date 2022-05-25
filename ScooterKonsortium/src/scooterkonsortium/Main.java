package scooterkonsortium;
import database.*;
import konsortiumdata.KonsortiumData;

public class Main {
	
	private static UserInterface oUserInterface;
	private static Datenbank oDatenbank;
	private static KonsortiumData oData;
	
	
	public static void main(String[] args) {
		oData = new KonsortiumData();
		oUserInterface = new UserInterface(oData);
		oDatenbank = new Datenbank();
		oUserInterface.mainLoop();
	}
	
	public static void mainLoop() {
		while (true) {
			oUserInterface.showMenu();
		}
	}

}

package scooterkonsortium;
import database.*;

public class Main {
	
	private static UserInterface oUserInterface;
	private static Datenbank oDatenbank;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		oUserInterface = new UserInterface();
		oDatenbank = new Datenbank();
		mainLoop();
	}
	
	public static void mainLoop() {
		while (true) {
			oUserInterface.showMenu();
		}
	}

}

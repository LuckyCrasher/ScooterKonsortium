package scooterkonsortium;

import java.util.Scanner;

import konsortiumdata.*;
import mapping.*;

public class UserInterface {
	private Map oMap = new Map();

	private Menus menus;
	private Scanner sc;
	private String selMenu = "main";

	public UserInterface() {
		this.sc = new Scanner(System.in);
		this.menus = new Menus(sc);

		createMenus();
	}

	private void changeMode() {
		System.out.println("CHANGING MODE");
	}

	private void createMenus() {
		String[] entries1 = new String[] { "Go to Operative mode", "Go to Setup mode" };
		char[] controls1 = new char[] { 'O', 'S' };
		Runnable[] functions1 = new Runnable[] { () -> selMenu = "operative", () -> selMenu = "setup" };
		menus.createMenu("main", entries1, controls1, functions1);

		String[] entries2 = new String[] { "Go to Operative mode" };
		char[] controls2 = new char[] { 'O' };
		Runnable[] functions2 = new Runnable[] { () -> selMenu = "operative" };
		menus.createMenu("operative", entries2, controls2, functions2);
		// opertive vorher Setup
		String[] entries3 = new String[] { "Go to Setup Mode" };
		char[] controls3 = new char[] { 'S' };
		Runnable[] functions3 = new Runnable[] {this::SetupMode};
		menus.createMenu("setup", entries3, controls3, functions3);

		String[] entries4 = new String[] { "Create New Company" };
		char[] controls4 = new char[] { 'C' };
		Runnable[] functions4 = new Runnable[] { () -> selMenu = "new company"};
		menus.createMenu("new comapany", entries4, controls4, functions4);
	}

	public void showMenu() {
		menus.drawUserInterface(selMenu);
	}

	// Setup Mode:
	Ladepunkt testLadepunkt;
	Firma testFirma;
	Scooter testScooter;

	public void createLadepunkt(String psName, Koordinaten poFixKoord, int piLadeCap, int piCurrentUse) {
		testLadepunkt = new Ladepunkt(psName, poFixKoord, piLadeCap, piCurrentUse);
	}

	public void createLadepunktMethode() {
		this.sc = new Scanner(System.in);
		System.out.println("Name des Ladepunktes");
		String temp = sc.nextLine();

		System.out.println("Koordinaten des Ladepunktes");
		// Koordinaten temp2 = sc.nextObject();
		// nextobject oder sowas ähnliches gibt es nicht, was soll ich dann machen
		System.out.println("Wieviele Ladestellen hat der Ladepunkt");
		int temp3 = sc.nextInt();

		System.out.println("Wieviele Ladestellen werden gerade benutzt");
		int temp4 = sc.nextInt();

		this.createLadepunkt(temp, null, temp3, temp4);
	}

	public void createFirma(String psNameFirma, double pdKostenJeFahrt, String psAdresse, int piPLZ, String psStadt,
			String psHotline) {
		testFirma = new Firma(psNameFirma, pdKostenJeFahrt, psAdresse, piPLZ, psStadt, psHotline);
	}

	public void createFirmaMethod() {
		// die ganzen Temp Variablen kann man bestimmt in einer ArrayList oder so machen
		this.sc = new Scanner(System.in);
		System.out.println("Name der Firma");
		String temp = sc.nextLine();

		System.out.println("Adresse der Firma");
		String temp2 = sc.nextLine();

		System.out.println("Postleitzahl der Firma");
		int temp3 = sc.nextInt();

		System.out.println("Stadt der Firma");
		String temp4 = sc.nextLine();

		System.out.println("Hotline der Firma");
		String temp5 = sc.nextLine();

		this.createFirma(temp, 5, temp2, temp3, temp4, temp5);
	}

	public void createScooter(Koordinaten poCurrentKoord, int piCurrentProzent, double pdCurrentEarn, int piCoveredKm,
			boolean pbCurrentStatus) {
		testScooter = new Scooter(poCurrentKoord, piCurrentProzent, pdCurrentEarn, piCoveredKm, pbCurrentStatus);
	}

	public void createScooterMethod() {
		this.sc = new Scanner(System.in);
		System.out.println("Aktueller Standort des Scooters");
		//Koordinaten temp = sc.nextLine();

		System.out.println("Aktueller Ladestand des Scooters");
		int temp2 = sc.nextInt();

		System.out.println("Wieviel Geld hat der Scooter produziert");
		double temp3 = sc.nextDouble();

		System.out.println("Zurückgelegte Kilometer");
		int temp4 = sc.nextInt();

		System.out.println("Aktueller Status");
		boolean temp5 = sc.nextBoolean();

		this.createScooter(null, temp2, temp3, temp4, temp5);
	}

	private void SetupMode() {
		System.out.println("Hello World");
		this.selMenu = "new company";
	}

}

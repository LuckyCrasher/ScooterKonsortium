package scooterkonsortium;

import java.util.Scanner;

import konsortiumdata.*;
import mapping.*;

public class UserInterface {
	private Map oMap = new Map();
	private KonsortiumData oData;
	
	private Menus menus;
	private Scanner sc;
	private String selMenu = "main";

	/*
	 *  create temporary objects
	 *  These will be filled and the references will be added where needed.
	 *  Then these attributes will be assigned new empty temporary objects
	 */
	private Firma tmpFirma;
	private Scooter tmpScooter;
	private Ladepunkt tmpLadepunkt;
	private boolean bRunning;
	
	public UserInterface(KonsortiumData oData) {
		this.oData = oData;
		
		this.sc = new Scanner(System.in);
		this.menus = new Menus(sc);
		this.bRunning = true;
		
		this.tmpFirma = new Firma();
		this.tmpScooter = new Scooter();
		this.tmpLadepunkt = new Ladepunkt();
		
		createMenus();
	}
	public KonsortiumData getdata() {
		return oData.toString();
	}

	private void createMenus() {
		/* 
		 * Main Menu
		 * Erlaubt es einen Modus zu wählen 
		*/
		
		String[] entries1 = new String[] { "Go to Operative mode", "Go to Setup mode", "Quit" };
		char[] controls1 = new char[] { 'O', 'S', 'Q'};
		Runnable[] functions1 = new Runnable[] { () -> selMenu = "operative", () -> selMenu = "setup", ()->this.bRunning=false};
		menus.createMenu("main", this, entries1, controls1, functions1);

		/* 
		 * Operative Mode
		 * Macht nicht viel
		 */
		String[] entries2 = new String[] { "Go to Setup mode", "Back" };
		char[] controls2 = new char[] { 'S', 'B' };
		Runnable[] functions2 = new Runnable[] { () -> selMenu = "setup", ()->selMenu="main"};
		menus.createMenu("operative", this, entries2, controls2, functions2);
		
		
		/*
		 * Setup Mode
		 * erlaubt das erstellen von Elementen
		 */
		
		String[] entries3 = new String[] { "Go to Operative Mode", "Create new company", "Create new Ladepunkt", "Back"};
		char[] controls3 = new char[] { 'O', 'C', 'L', 'B'};
		Runnable[] functions3 = new Runnable[] {()->this.selMenu="operative", ()->selMenu="new company", ()->selMenu="new Ladepunkt", ()->selMenu="main"};
		menus.createMenu("setup", this, entries3, controls3, functions3);

		/*
		 * Create Firma Menu
		 * erlaubt es werte für die neue Firma zu setzen
		 */
		
		String[] entries4 = new String[] { "Set name",
				"Set road",
				"Set post code",
				"Set city",
				"Set hotline",
				"Back (Abort)",
				"Save"};
		char[] controls4 = new char[] { 'N', 'R', 'P', 'C', 'H', 'B', 'S'};
		Runnable[] functions4 = new Runnable[] {
				()->this.tmpFirma.setName(getUserStringInput("Company name")),
				()->this.tmpFirma.setAdresse(getUserStringInput("Road name")),
				()->this.tmpFirma.setPLZ(this.getUserIntInput("Company post code")),
				()->this.tmpFirma.setStadt(this.getUserStringInput("Company city")),
				()->this.tmpFirma.setHotline(this.getUserStringInput("Company hotline")),
				()->this.selMenu="setup",
				this::saveCompany
				};
		menus.createMenu("new company", this, entries4, controls4, functions4);
			
		/*
		 * Create Ladepunkt Menu
		 * erlaubt es Werte f�r den neuen Ladepunkt zu setzen
		 */
		
		String [] entries5 = new String[] {"Set name",
				"Set Koordinaten",
				"Set Lade Capacity",
				"Set Current Usage",
				"Back (Abort)",
				"Save"};
		char[] controls5 = new char[] { 'N', 'K', 'C', 'U', 'B', 'S'};
		Runnable[] functions5 = new Runnable[] {
				()-> this.tmpLadepunkt.setNameLadepunkt(getUserStringInput("Ladepunkt name")),
				()-> this.selMenu="KoordinatenUntermenu",
				()-> this.tmpLadepunkt.setLadeCap(getUserIntInput("Ladepunkt Capacity")),
				()-> this.tmpLadepunkt.setCurrentUse(getUserIntInput("Ladepunkt Usage")),
				()-> this.selMenu="setup",
				this::saveLadepunkt
				};
		menus.createMenu("new Ladepunkt", this, entries5, controls5, functions5);
		
		/*
		 * Koordinaten untermen�
		 */
		
		String[] entries6 = new String[] {"Set KoordinatenX",
				"Set KoordinatenY",
				"Back (Abort)",};
		char[] controls6 = new char[] {'X', 'Y'};
		Runnable[] functions6 = new Runnable[] {
				()-> this.tmpLadepunkt.setx(getUserIntInput("Koordinaten X")),
				()-> this.tmpLadepunkt.sety(getUserIntInput("Koordinaten Y")),
				()-> this.selMenu="setup",
		};
		menus.createMenu("KoordinatenUntermenu", this, entries6, controls6, functions6);
	}
	
	private String getUserStringInput(String sPrompt) {
		System.out.printf("%s ->", sPrompt);
		return sc.nextLine();
	}
	
	private int getUserIntInput(String sPrompt) {
		System.out.printf("%s ->", sPrompt);
		return sc.nextInt();
	}
	
	
	
	public void showMenu() {
		menus.drawUserInterface(selMenu);
	}
	
	private void saveCompany() {
		System.out.println("Saving Firma");
		this.oData.addFirma(tmpFirma);
		this.tmpFirma = null;
		this.tmpFirma = new Firma();
		this.selMenu = "setup";
	}
	private void saveLadepunkt() {
		System.out.println("Saving Ladepunkt");
		this.oData.addladepunkt(this.tmpFirma.getName(),tmpLadepunkt);
		this.tmpLadepunkt = null;
		this.tmpLadepunkt = new Ladepunkt();
		this.selMenu = "setup";
	}

	public void mainLoop() {
		while (this.bRunning) {
			showMenu();
		}
	}

}

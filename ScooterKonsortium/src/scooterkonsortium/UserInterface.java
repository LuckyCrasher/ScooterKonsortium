package scooterkonsortium;

import java.util.Scanner;
import java.util.Stack;

import konsortiumdata.*;
import mapping.*;

public class UserInterface {
	private Map oMap;
	private KonsortiumData oData;
	
	private Menus menus;
	private Scanner sc;
	private Stack<String> selMenu = new Stack<>();

	/*
	 *  create temporary objects
	 *  These will be filled and the references will be added where needed.
	 *  Then these attributes will be assigned new empty temporary objects
	 */
	private Firma tmpFirma;
	private Scooter tmpScooter;
	private Ladepunkt tmpLadepunkt;
	private int tmpX;
	private int tmpY;
	private boolean bRunning;
	
	public UserInterface(KonsortiumData oData) {
		this.oData = oData;
		this.oMap = new Map(oData, 10, 10);
		
		this.sc = new Scanner(System.in);
		this.menus = new Menus(oData, sc);
		this.bRunning = true;
		
		this.tmpFirma = new Firma();
		this.tmpScooter = new Scooter();
		this.tmpLadepunkt = new Ladepunkt();
		this.tmpX = 0;
		this.tmpY = 0;
		
		createMenus();
		selMenu.add("main");
	}
	

	private void createMenus() {
		/* 
		 * Main Menu
		 * Erlaubt es einen Modus zu wählen 
		*/
		
		String[] entries1 = new String[] { "Go to Operative mode", "Go to Setup mode", "Quit" };
		char[] controls1 = new char[] { 'O', 'S', 'Q'};
		Runnable[] functions1 = new Runnable[] {
				() -> selMenu.push("operative"),
				() -> selMenu.push("setup"),
				()->this.bRunning=false};
		menus.createMenu("main", entries1, controls1, functions1);

		/* 
		 * Operative Mode
		 * Macht nicht viel
		 */
		String[] entries2 = new String[] {"Show Map", "Back" };
		char[] controls2 = new char[] {'M', 'B' };
		Runnable[] functions2 = new Runnable[] {
				()->this.setShowData(this.oMap),
				()->selMenu.pop()};
		menus.createMenu("operative", entries2, controls2, functions2);
		
		
		/*
		 * Setup Mode
		 * erlaubt das erstellen von Elementen
		 */
		
		String[] entries3 = new String[] {"Create new company", "Create new Ladepunkt", "Create new Scooter", "Back"};
		char[] controls3 = new char[] {'C', 'L', 'S', 'B'};
		Runnable[] functions3 = new Runnable[] {
				()->{selMenu.push("new company");this.setShowData(this.tmpFirma);},
				()->{selMenu.push("new Ladepunkt");this.setShowData(this.tmpLadepunkt);},
				()->{selMenu.push("new Scooter");this.setShowData(this.tmpScooter);},
				()->selMenu.pop()};
		menus.createMenu("setup", entries3, controls3, functions3);

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
				()->this.selMenu.pop(),
				this::saveCompany
				};
		menus.createMenu("new company", entries4, controls4, functions4);
			
		/*
		 * Create Ladepunkt Menu
		 * erlaubt es Werte f�r den neuen Ladepunkt zu setzen
		 */
		
		String [] entries5 = new String[] {"Set name",
				"Set Koordinaten",
				"Set Lade Capacity",
				"Set Current Usage",
				"Set company owing",
				"Back (Abort)",
				"Save"};
		char[] controls5 = new char[] { 'N', 'K', 'C', 'U', 'F', 'B', 'S'};
		Runnable[] functions5 = new Runnable[] {
				()-> this.tmpLadepunkt.setNameLadepunkt(getUserStringInput("Ladepunkt name")),
				()-> this.selMenu.push("KoordinatenUntermenu"),
				()-> this.tmpLadepunkt.setLadeCap(getUserIntInput("Ladepunkt Capacity")),
				()-> this.tmpLadepunkt.setCurrentUse(getUserIntInput("Ladepunkt Usage")),
				()-> this.tmpLadepunkt.setOwnedBy(getUserStringInput("Owned by")),
				()-> this.selMenu.pop(),
				this::saveLadepunkt
				};
		menus.createMenu("new Ladepunkt", entries5, controls5, functions5);
		
		
		
		/*
		 * Create Scooter Menu
		 * erlaubt es Werte f�r den neuen Scooter zu setzen
		 */
		
		String[] entries7 = new String[] {
				"Set Koordinaten",
				"Set current Prozent",
				"Set current Earnings",
				"Set covered Kilometers",
				"Set current status",
				"Set Firma owning",
				"Back (Abort)",
				"Save"};
		char[] controls7 = new char[] {'K', 'P', 'E', 'M', 'L', 'F', 'B', 'S'};
		Runnable[] functions7 = new Runnable[] {
				()-> this.selMenu.push("KoordinatenUntermenu"),
				()-> this.tmpScooter.setCurrentProzent(getUserIntInput("Current Percent")),
				()-> this.tmpScooter.setCurrentEarn(getUserDoubleInput("Current Earnings")),
				()-> this.tmpScooter.setCoveredKm(getUserIntInput("Covered Km")),
				()-> this.tmpScooter.setCurrentStatus(getUserBooleanInput("Current Loading Status")),
				()-> this.tmpScooter.setOwnedBy(getUserStringInput("Owned by")),
				()-> this.selMenu.pop(),
				this::saveScooter
		};
		menus.createMenu("new Scooter", entries7, controls7, functions7);
		
		/*
		 * Koordinaten untermenue
		 */
		
		String[] entries6 = new String[] {"Set KoordinatenX",
				"Set KoordinatenY",
				"Back (Abort)"};
		char[] controls6 = new char[] {'X', 'Y', 'B'};
		Runnable[] functions6 = new Runnable[] {
				()-> this.tmpX = getUserIntInput("Koordinaten X"),
				()-> this.tmpY = getUserIntInput("Koordinaten Y"),
				()-> this.selMenu.pop()
		};
		menus.createMenu("KoordinatenUntermenu", entries6, controls6, functions6);
	
	
	}
	
	private String getUserStringInput(String sPrompt) {
		System.out.printf("%s ->", sPrompt);
		return sc.nextLine();
	}
	
	private int getUserIntInput(String sPrompt) {
		System.out.printf("%s ->", sPrompt);
		return sc.nextInt();
	}
	

	private double getUserDoubleInput(String sPrompt) {
		System.out.printf("%s ->", sPrompt);
		return sc.nextDouble();
	}
	
	private boolean getUserBooleanInput(String sPrompt) {
		System.out.printf("%s ->", sPrompt);
		return sc.nextBoolean();
	}
	
	public void showMenu() {
		menus.drawUserInterface(selMenu.peek());
	}
	
	private void saveCompany() {
		System.out.println("Saving Firma");
		this.oData.addFirma(tmpFirma);
		this.tmpFirma = null;
		this.tmpFirma = new Firma();
		this.setShowData(null);
		this.selMenu.pop();
	}
	private void saveLadepunkt() {
		System.out.println("Saving Ladepunkt");
		if(!this.oData.containsCompany(this.tmpLadepunkt.getFirmaOwning())) {
			System.err.println("Can not add Ladepunkt to company! The company does not exist.");
			return;
		}
		
		this.tmpLadepunkt.x = this.tmpX;
		this.tmpLadepunkt.y = this.tmpY;
		this.tmpX = 0;
		this.tmpY = 0;
		this.oData.addladepunkt(this.tmpLadepunkt.getFirmaOwning(),tmpLadepunkt);
		this.tmpLadepunkt = null;
		this.tmpLadepunkt = new Ladepunkt();
		this.setShowData(null);
		this.selMenu.pop();
	}
	
	private void saveScooter() {
		System.out.println("Saving Scooter");
		if(!this.oData.containsCompany(this.tmpScooter.getFirmaOwning())) {
			System.err.println("Can not add scooter to company! The company does not exist.");
			return;
		}
		this.oData.addScooter(this.tmpScooter.getFirmaOwning(), tmpScooter);
		this.tmpScooter = null;
		this.tmpScooter = new Scooter();
		this.selMenu.pop();
	}

	public void mainLoop() {
		while (this.bRunning) {
			showMenu();
		}
	}
	
	public void setShowData(Object oShowData) {
		this.menus.setShowData(oShowData);
	}
	
}

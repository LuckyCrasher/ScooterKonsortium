package scooterkonsortium;

import java.util.Scanner;
import java.util.Stack;
import mapping.*;

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
	 *  These will be filled and the references will be
	 * added where needed. Then these attributes will be assigned new empty
	 * temporary objects
	 */
	private Firma tmpFirma;
	private Scooter tmpScooter;
	private Ladepunkt tmpLadepunkt;
	private Koordinaten tmpKoord;
	private boolean bRunning;
	private Runnable Runback; 

	public UserInterface(KonsortiumData oData) {
		this.oData = oData;
		this.oMap = new Map(oData, 10, 10);

		this.sc = new Scanner(System.in);
		this.menus = new Menus(oData, sc);
		this.bRunning = true;

		this.tmpFirma = new Firma();
		this.tmpScooter = new Scooter();
		this.tmpLadepunkt = new Ladepunkt();
		this.tmpKoord = new Koordinaten();

		createMenus();
		selMenu.add("main");
	}

	private void createMenus() {
		/*
		 * Main Menu Erlaubt es einen Modus zu wählen
		 */

		String[] entries1 = new String[] { "Go to Operative mode", "Go to Setup mode", "Quit" };
		char[] controls1 = new char[] { 'O', 'S', 'Q' };
		Runnable[] functions1 = new Runnable[] {
				() -> selMenu.push("operative"),
				() -> selMenu.push("setup"),
				() -> this.bRunning = false
				};
		menus.createMenu("main", entries1, controls1, functions1);

		/*
		 * Operative Mode Macht nicht viel
		 */
		String[] entries2 = new String[] { "Show Map", "Move Scooter", "Back" };
		char[] controls2 = new char[] { 'S', 'M', 'B' };
		Runnable[] functions2 = new Runnable[] {
				() -> this.pushShowData(this.oMap),
				() -> {
					selMenu.push("Move Scooter");
					this.pushShowData(new Object[]{this.tmpScooter, "--Target--", this.tmpKoord});
				},
				() -> selMenu.pop()
				};
		menus.createMenu("operative", entries2, controls2, functions2);

		/*
		 * Setup Mode erlaubt das erstellen von Elementen
		 */
		String[] entries8 = new String[] { "Company Operations", "Ladepunkt Operations", "Scooter Operations", "Back" };
		char[] controls8 = new char[] { 'C', 'L', 'S', 'B' };
		Runnable[] functions8 = new Runnable[] {
				() -> selMenu.push("company menu"),
				() -> selMenu.push("Ladepunkt menu"),
				() -> selMenu.push("Scooter menu"),
				() -> selMenu.pop()
				};
		menus.createMenu("setup", entries8, controls8, functions8);

		// OPERATIVE SUB MENUS BEGIN
		
		String[] entries14 = new String[] { "Select Scooter", "Select Destination", "Move Scooter", "Back" };
		char[] controls14 = new char[] { 'S', 'D', 'M', 'B' };
		Runnable[] functions14 = new Runnable[] {
				() -> {
					selMenu.push("Select Scooter");
					this.pushShowData(this.tmpScooter);
				},
				() -> {
					this.selMenu.push("KoordinatenUntermenu");
					this.pushShowData(this.tmpKoord);
					},
				() -> System.err.println("Warning this Feature is Unimplemented!!!"),
				() -> {
					selMenu.pop();
					this.popShowData();
				}
				};
		menus.createMenu("Move Scooter", entries14, controls14, functions14);
		
		String[] entries15 = new String[] { "Firma Owning", "Koordinaten", "Load", "Back (Abort)" };
		char[] controls15 = new char[] { 'F', 'K', 'L', 'B' };
		Runnable[] functions15 = new Runnable[] {
				() -> this.tmpScooter.setOwnedBy(this.getUserStringInput("Firma Owning")),
				() -> {
					this.selMenu.push("KoordinatenUntermenu");
					this.pushShowData(this.tmpKoord);
					},
				() -> this.loadScooter(),
				() -> selMenu.pop()
				};
		menus.createMenu("Select Scooter", entries15, controls15, functions15);
		
		// OPERATIVE SUB MENUS END
		
		// SETUP SUB MENUS BEGIN
		
		// Company Sub menu
		String[] entries9 = new String[] { "New Company", "Delete Company", "Back" };
		char[] controls9 = new char[] { 'N', 'D', 'B' };
		Runnable[] functions9 = new Runnable[] {
				() -> {
					selMenu.push("new company");
					this.pushShowData(this.tmpFirma);
				},
				() -> selMenu.push("delete company"),
				() -> {
					selMenu.pop();
					
				}
				};
		menus.createMenu("company menu", entries9, controls9, functions9);

		// Ladepunkt Sub menu
		String[] entries10 = new String[] { "New Ladepunkt", "Delete Ladepunkt", "Back" };
		char[] controls10 = new char[] { 'N', 'D', 'B' };
		Runnable[] functions10 = new Runnable[] {
				() ->{
					selMenu.push("new Ladepunkt");
					this.pushShowData(this.tmpLadepunkt);
				},
				() -> selMenu.push("delete Ladepunkt"),
				() -> selMenu.pop()
				};
		menus.createMenu("Ladepunkt menu", entries10, controls10, functions10);

		// Scooter Sub menu
		String[] entries11 = new String[] { "New Scooter", "Delete Scooter", "Back" };
		char[] controls11 = new char[] { 'N', 'D', 'B' };
		Runnable[] functions11 = new Runnable[] {
				() -> {
					selMenu.push("new Scooter");
					this.pushShowData(this.tmpScooter);
				},
				() -> selMenu.push("delete Scooter"),
				() -> selMenu.pop()
				};
		menus.createMenu("Scooter menu", entries11, controls11, functions11);

		String[] entries12 = new String[] { "Select Company by Name", "Back" };
		char[] controls12 = new char[] { 'S', 'B' };
		Runnable[] functions12 = new Runnable[] {
				() -> this.deleteCompany(),
				()->selMenu.pop()
				};
		menus.createMenu("delete company", entries12, controls12, functions12);
		
		
		String[] entries13 = new String[] {"Select Ladepunkt by Koordinate", "Back"};
		
		/*
		 * String[] entries3 = new String[] {"Create new company",
		 * "Create new Ladepunkt", "Create new Scooter", "Back"}; char[] controls3 = new
		 * char[] {'C', 'L', 'S', 'B'}; Runnable[] functions3 = new Runnable[] {
		 * ()->{selMenu.push("new company");this.setShowData(this.tmpFirma);},
		 * ()->{selMenu.push("new Ladepunkt");this.setShowData(this.tmpLadepunkt);},
		 * ()->{selMenu.push("new Scooter");this.setShowData(this.tmpScooter);},
		 * ()->selMenu.pop()}; menus.createMenu("setup", entries3, controls3,
		 * functions3);
		 */

		/*
		 * Create Firma Menu erlaubt es werte für die neue Firma zu setzen
		 */

		String[] entries4 = new String[] { "Set name", "Set road", "Set post code", "Set city", "Set hotline",
				"Back (Abort)", "Save" };
		char[] controls4 = new char[] { 'N', 'R', 'P', 'C', 'H', 'B', 'S' };
		Runnable[] functions4 = new Runnable[] {
				() -> this.tmpFirma.setName(getUserStringInput("Company name")),
				() -> this.tmpFirma.setAdresse(getUserStringInput("Road name")),
				() -> this.tmpFirma.setPLZ(this.getUserIntInput("Company post code")),
				() -> this.tmpFirma.setStadt(this.getUserStringInput("Company city")),
				() -> this.tmpFirma.setHotline(this.getUserStringInput("Company hotline")),
				() -> this.selMenu.pop(),
				this::saveCompany };
		menus.createMenu("new company", entries4, controls4, functions4);

		/*
		 * Create Ladepunkt Menu erlaubt es Werte f�r den neuen Ladepunkt zu setzen
		 */

		String[] entries5 = new String[] { "Set name", "Set Koordinaten", "Set Lade Capacity", "Set Current Usage",
				"Set company owing", "Back (Abort)", "Save" };
		char[] controls5 = new char[] { 'N', 'K', 'C', 'U', 'F', 'B', 'S' };
		Runnable[] functions5 = new Runnable[] {
				() -> this.tmpLadepunkt.setNameLadepunkt(getUserStringInput("Ladepunkt name")),
				() -> {
					this.selMenu.push("KoordinatenUntermenu");
					this.pushShowData(this.tmpKoord);
					},
				() -> this.tmpLadepunkt.setLadeCap(getUserIntInput("Ladepunkt Capacity")),
				() -> this.tmpLadepunkt.setCurrentUse(getUserIntInput("Ladepunkt Usage")),
				() -> this.tmpLadepunkt.setOwnedBy(getUserStringInput("Owned by")),
				() -> this.selMenu.pop(),
				this::saveLadepunkt };
		menus.createMenu("new Ladepunkt", entries5, controls5, functions5);

		/*
		 * Create Scooter Menu erlaubt es Werte f�r den neuen Scooter zu setzen
		 */

		String[] entries7 = new String[] { "Set Koordinaten", "Set current Prozent", "Set current Earnings",
				"Set covered Kilometers", "Set current status", "Set Firma owning", "Back (Abort)", "Save" };
		char[] controls7 = new char[] { 'K', 'P', 'E', 'M', 'L', 'F', 'B', 'S' };
		Runnable[] functions7 = new Runnable[] {
				() -> {
					this.selMenu.push("KoordinatenUntermenu");
					this.pushShowData(this.tmpKoord);
					},
				() -> this.tmpScooter.setCurrentProzent(getUserIntInput("Current Percent")),
				() -> this.tmpScooter.setCurrentEarn(getUserDoubleInput("Current Earnings")),
				() -> this.tmpScooter.setCoveredKm(getUserIntInput("Covered Km")),
				() -> this.tmpScooter.setCurrentStatus(getUserBooleanInput("Current Loading Status")),
				() -> this.tmpScooter.setOwnedBy(getUserStringInput("Owned by")),
				() -> this.selMenu.pop(),
				this::saveScooter };
		menus.createMenu("new Scooter", entries7, controls7, functions7);

		// SETUP SUB MENUS END
		
		/*
		 * Koordinaten untermenue
		 */

		String[] entries6 = new String[] { "Set KoordinatenX", "Set KoordinatenY", "Save", "Back (Abort)" };
		char[] controls6 = new char[] { 'X', 'Y', 'S', 'B' };
		Runnable[] functions6 = new Runnable[] {
				() -> this.tmpKoord.setx(getUserIntInput("Koordinaten X")),
				() -> this.tmpKoord.sety(getUserIntInput("Koordinaten Y")),
				() -> this.leaveKoordMenu(),
				() -> selMenu.pop()
				};
		menus.createMenu("KoordinatenUntermenu", entries6, controls6, functions6);
	}

	private void leaveKoordMenu() {
		this.selMenu.pop();
		this.tmpLadepunkt.x = this.tmpKoord.getx();
		this.tmpLadepunkt.y = this.tmpKoord.gety();

		this.tmpScooter.x = this.tmpKoord.getx();
		this.tmpScooter.y = this.tmpKoord.gety();
		this.popShowData();
	}

	/*
	 * Get User input in various forms
	 */
	
	private String getUserStringInput(String sPrompt) {
		System.out.printf("%s ->", sPrompt);
		return sc.nextLine();
	}

	private int getUserIntInput(String sPrompt) {
		System.out.printf("%s ->", sPrompt);
		int i = sc.nextInt();
		sc.nextLine();
		return i;
	}

	private double getUserDoubleInput(String sPrompt) {
		System.out.printf("%s ->", sPrompt);
		double d = sc.nextDouble();
		sc.nextLine();
		return d;
	}

	private boolean getUserBooleanInput(String sPrompt) {
		System.out.printf("%s ->", sPrompt);
		boolean b = sc.nextBoolean();
		sc.nextLine();
		return b;
	}

	private void saveCompany() {
		System.out.println("Saving Firma");
		this.oData.addFirma(tmpFirma);
		this.tmpFirma = null;
		this.tmpFirma = new Firma();
		this.selMenu.pop();
		this.popShowData();
	}

	private void deleteCompany() {
		System.out.println("Deleting  Firma");
		this.oData.deleteFirma(getUserStringInput("Company Name"));
	}

	private void saveLadepunkt() {
		System.out.println("Saving Ladepunkt");
		if (!this.oData.containsCompany(this.tmpLadepunkt.getFirmaOwning())) {
			System.err.println("Can not add Ladepunkt to company! The company does not exist.");
			return;
		}

		this.oData.addladepunkt(this.tmpLadepunkt.getFirmaOwning(), tmpLadepunkt);
		this.tmpLadepunkt = null;
		this.tmpLadepunkt = new Ladepunkt();
		this.popShowData();
		this.selMenu.pop();
	}
	
	private void loadLadepunkt(String sFirmaName) {
		this.tmpLadepunkt = oData.getLadepunkt(sFirmaName, "");
	}

	private void saveScooter() {
		System.out.println("Saving Scooter");
		if (!this.oData.containsCompany(this.tmpScooter.getFirmaOwning())) {
			System.err.println("Can not add scooter to company! The company does not exist.");
			return;
		}
		this.oData.addScooter(this.tmpScooter.getFirmaOwning(), tmpScooter);
		this.tmpScooter = null;
		this.tmpScooter = new Scooter();
		this.popShowData();
		this.selMenu.pop();
	}
	
	private void loadScooter() {
		//To load scooter the data for the scooter to be searched is stored in tmpScooter
		Scooter s = this.oData.getScooter(this.tmpScooter.getFirmaOwning(), this.tmpScooter.x, this.tmpScooter.y);
		this.tmpScooter = s;
		if(this.tmpScooter == null) {
			this.tmpScooter = new Scooter();
		} else {
			this.popShowData();
			this.pushShowData(new Object[]{this.tmpScooter, "--Target--", this.tmpKoord});
		}
	}

	public void mainLoop() {
		while (this.bRunning) {
			showMenu();
		}
	}

	public void popShowData() {
		this.menus.popShowData();
	}
	
	public void pushShowData(Object oShowData) {
		this.menus.pushShowData(oShowData);
	}
	
	public void showMenu() {
		menus.drawUserInterface(selMenu.peek());
	}

}

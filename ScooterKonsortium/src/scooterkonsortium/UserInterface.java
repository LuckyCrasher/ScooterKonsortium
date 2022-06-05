package scooterkonsortium;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

import database.Datenbank;
import mapping.*;

import konsortiumdata.*;
import mapping.*;

public class UserInterface {
	private Map oMap;
	private KonsortiumData oData;
	private Datenbank oDatenbank;
	
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
	private Object map;
	
	

	public UserInterface(KonsortiumData oData, Datenbank oDatenbank) {
		this.oData = oData;
		this.oMap = new Map(oData, 10, 10);

		this.sc = new Scanner(System.in);
		this.menus = new Menus(oData, sc);
		this.bRunning = true;

		this.tmpFirma = new Firma();
		this.tmpScooter = new Scooter();
		this.tmpLadepunkt = new Ladepunkt();
		this.tmpKoord = new Koordinaten();

		this.menus.pushCallback(() -> System.out.println("No callback defined"));
		
		createMenus();
		selMenu.add("main");
		this.oDatenbank = oDatenbank;
	}

	private void createMenus() {
		/*
		 * Main Menu Erlaubt es einen Modus zu wählen
		 */

		String[] entries1 = new String[] { "Go to Operative mode", "Go to Setup mode", "Database Settings", "Connect to Database", "Quit" };
		char[] controls1 = new char[] { 'O', 'S', 'D', 'C', 'Q' };
		Runnable[] functions1 = new Runnable[] {
				() -> selMenu.push("operative"),
				() -> selMenu.push("setup"),
				() -> {
					this.pushShowData(oDatenbank);
					selMenu.push("Database Settings");
				},
				() -> this.connectToDatabase(),
				() -> this.bRunning = false
				};
		menus.createMenu("main", entries1, controls1, functions1);

		/*
		 * Database Settings controls relevant Settings
		 */
		
		String[] entries17 = new String[] {"Set Server", "Set User", "Set Password", "Set Database", "Save"};
		char[] controls17 = new char[] { 'A', 'U', 'P', 'D', 'S' };
		Runnable[] functions17 = new Runnable[] {
				() -> this.oDatenbank.setServer(this.getUserStringInput("MySQL Server")),
				() -> this.oDatenbank.setUser(this.getUserStringInput("MySQL User")),
				() -> this.oDatenbank.setPassword(this.getUserStringInput("MySQL Password")),
				() -> this.oDatenbank.setDatabase(this.getUserStringInput("MySQL Database")),
				() -> {
					this.popShowData();
					selMenu.pop();
				}
				};
		menus.createMenu("Database Settings", entries17, controls17, functions17);
		
		/*
		 * Operative Mode Macht nicht viel
		 */
		String[] entries2 = new String[] { "Show Map", "Move Scooter", "Load from Database", "Save to Database", "Back" };
		char[] controls2 = new char[] { 'S', 'M', 'B' };

		Runnable[] functions2 = new Runnable[] {
				() -> this.pushShowData(this.oMap),
				() -> {
					selMenu.push("Select Scooter");
					this.pushShowData(this.tmpScooter);
				},
				() -> selMenu.pop()
				};
		menus.createMenu("operative", entries2, controls2, functions2);

		/*
		 * Setup Mode erlaubt das erstellen von Elementen
		 */
		String[] entries8 = new String[] { "Company Operations", "Ladepunkt Operations", "Scooter Operations", "Calc Distances","Show Map", "Back" };
		char[] controls8 = new char[] { 'C', 'L', 'S', 'D','M', 'B' };
		Runnable[] functions8 = new Runnable[] {
				() -> selMenu.push("company menu"),
				() -> selMenu.push("Ladepunkt menu"),
				() -> selMenu.push("Scooter menu"),
				() -> this.pushShowData(this.oMap.getCaluclateDistances()),
				() -> this.pushShowData(this.oMap),
				() -> selMenu.pop()
				};
		menus.createMenu("setup", entries8, controls8, functions8);

		// OPERATIVE SUB MENUS BEGIN
		
		String[] entries14 = new String[] {"Select Destination", "Move Scooter", "Back" };
		char[] controls14 = new char[] {'D', 'M', 'B' };
		Runnable[] functions14 = new Runnable[] {
				() -> {
					this.menus.pushCallback(() -> {
						this.tmpKoord.x = this.tmpKoord.getx();
						this.tmpKoord.y = this.tmpKoord.gety();
					});
					this.selMenu.push("KoordinatenUntermenu");
					this.pushShowData(this.tmpKoord);
					},
				() -> this.moveScooter(this.tmpKoord.x, this.tmpKoord.y),
				() -> {
					selMenu.pop();
					this.popShowData();
				}
				};
		menus.createMenu("Move Scooter", entries14, controls14, functions14);
		
		String[] entries18 = new String[] {"Move Scooter", "Back" };
		char[] controls18 = new char[] {'M', 'B'};
		Runnable[] functions18 = new Runnable[] {
				() -> {
					Ladepunkt l = this.oMap.getNearestLadepunkt(this.tmpScooter);
					this.moveScooter(l.x, l.y);
				},
				() -> {
					selMenu.pop();
					this.popShowData();
				}
				};
		menus.createMenu("Force Move Scooter", entries18, controls18, functions18);
		
		//select Scooter menu
		String[] entries15 = new String[] { "Firma Owning", "Koordinaten", "Load", "Back (Abort)" };
		char[] controls15 = new char[] { 'F', 'K', 'L', 'B' };
		Runnable[] functions15 = new Runnable[] {
				() -> this.tmpScooter.setOwnedBy(this.getUserStringInput("Firma Owning")),
				() -> {
					this.menus.pushCallback(() -> {
						this.tmpScooter.x = this.tmpKoord.getx();
						this.tmpScooter.y = this.tmpKoord.gety();
					});
					this.selMenu.push("KoordinatenUntermenu");
					this.pushShowData(this.tmpKoord);
					},
				() -> {
						this.loadScooter();
					},
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

		// delete Company Menu
		String[] entries12 = new String[] { "Select Company by Name", "Back" };
		char[] controls12 = new char[] { 'S', 'B' };
		Runnable[] functions12 = new Runnable[] {
				() -> this.deleteCompany(),
				() -> selMenu.pop()
				};
		menus.createMenu("delete company", entries12, controls12, functions12);
		
		//delete Ladepunkt Menu
		String[] entries13 = new String[] {"Firma Name", "Ladepunkt Name","Delete", "Back"};
		char[] controls13 = new char[] {'F','L','D','B'};
		Runnable [] functions13 = new Runnable[] {
				() -> this.tmpLadepunkt.setOwnedBy(this.getUserStringInput("Firma Owning")),
				() -> this.tmpLadepunkt.setNameLadepunkt(this.getUserStringInput("Ladepunkt Name")),
				() -> this.deleteLadepunkt(),
				() -> selMenu.pop()
				};
		menus.createMenu("delete Ladepunkt", entries13, controls13, functions13);
		
		//delete Scooter
		String[] entries16 = new String[] {"Firma Name", "Koordinaten Scooter","Delete", "Back"};
		char[] controls16 = new char[] {'F','S','D','B'};
		Runnable [] functions16 = new Runnable[] {
				() -> this.tmpScooter.setOwnedBy(this.getUserStringInput("Firma Owning")),
				() -> {
					this.menus.pushCallback(() -> {
						this.tmpScooter.x = this.tmpKoord.getx();
						this.tmpScooter.y = this.tmpKoord.gety();
					});
					this.selMenu.push("KoordinatenUntermenu");
					this.pushShowData(this.tmpKoord);
					},
				() -> this.deleteScooter(),
				() -> selMenu.pop()
				};
		menus.createMenu("delete Scooter", entries16, controls16, functions16);
		

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
					this.menus.pushCallback(() -> {
						this.tmpLadepunkt.x = this.tmpKoord.getx();
						this.tmpLadepunkt.y = this.tmpKoord.gety();
					});
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
					this.menus.pushCallback(() -> {
						this.tmpScooter.x = this.tmpKoord.getx();
						this.tmpScooter.y = this.tmpKoord.gety();
						this.compareKoords();
					});
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

	private void connectToDatabase() {
		if (!this.oDatenbank.connect()) {
			System.err.println("Connection to Database failed! Please enter valid connection settings");
		} else {
			System.out.println("Connected to Database");
		}
	}

	private void leaveKoordMenu() {
		this.selMenu.pop();
		this.menus.peekCallback().run();
		this.menus.popCallback();
		this.popShowData();
	}

	private void compareKoords() {
		Ladepunkt[] aoLadepunkte;
		for (String Name : oData.getFirmaNames()) {
			aoLadepunkte = oData.getLadepunkte(Name);
			for(int k = 0; k < aoLadepunkte.length;k++) {
				if (aoLadepunkte[k].getx() == this.tmpScooter.x && aoLadepunkte[k].gety() == this.tmpScooter.y) {
					System.err.println("Can not place Scooter! Same Coordinates as Ladepunkt");
				}
			}
		}
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
		int i;
		try {
			i = sc.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("Die eingabe war keine Zahl");
			e.printStackTrace();
			i = 0;
		}
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
	private void deleteLadepunkt() {
		System.out.println("Deleting Ladepunkt");
		this.oData.deleteLadepunkt(this.tmpLadepunkt.getFirmaOwning(),this.tmpLadepunkt.getNameLadepunkt());
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
	
	private void deleteScooter() {
		System.out.println("Deleting Scooter");
		this.oData.deleteScooter(this.tmpScooter.getFirmaOwning(), this.tmpScooter.x, this.tmpScooter.y);
	}
	
	private void loadScooter() {
		//To load scooter the data for the scooter to be searched is stored in tmpScooter
		Scooter s = this.oData.getScooter(this.tmpScooter.getFirmaOwning(), this.tmpScooter.x, this.tmpScooter.y);
		this.tmpScooter = s;
		if(this.tmpScooter == null) {
			this.tmpScooter = new Scooter();
		} else {
			this.popShowData();
			this.selMenu.pop();
			
			if(this.tmpScooter.getCurrentProzent()<30) {
				this.pushShowData(new Object[] {this.tmpScooter, this.oMap.caluclateNearestLadepunkt(this.tmpScooter)});
				this.selMenu.push("Force Move Scooter");
			} else {
				this.pushShowData(this.tmpScooter);
				this.selMenu.push("Move Scooter");
			}
			
		}
	}
	
	public void moveScooter(int x, int y) {
		double distance = oMap.calculateCost(this.tmpScooter.x, this.tmpScooter.y, x, y);
		int iDistance = (int) Math.round(distance);
		
		Firma owning = oData.getFirma(this.tmpScooter.getFirmaOwning());
		
		Ladepunkt l = this.oData.getLadepunkt(x, y);
		
		if(l != null && l.getCurrentUse() >= l.getLadeCap()) {
			System.err.println("The Scooter could not be moved to this position. The capacity of the Ladepunkt is reached");
			return;
		}
		if (l!=null) {
			l.setCurrentUse(l.getCurrentUse()+1);
			this.tmpScooter.setCurrentStatus(true);
			this.tmpScooter.setCurrentProzent(this.tmpScooter.getCurrentProzent()+10);
		}
		
		//check if moving away from Ladepunkt
		l = this.oData.getLadepunkt(this.tmpScooter.x, this.tmpScooter.y);
		if(l != null) {
			// we are moving away from Ladepunkt
			l.setCurrentUse(l.getCurrentUse()-1);
			this.tmpScooter.setCurrentStatus(false);
		}
	
		this.tmpScooter.setCurrentProzent(this.tmpScooter.getCurrentProzent()-iDistance);
		this.tmpScooter.setCoveredKm(this.tmpScooter.getCoveredKm() + iDistance);
		this.tmpScooter.setCurrentEarn(this.tmpScooter.getCurrentEarn() + iDistance * owning.getKostenJeFahrt());
		
		this.popShowData();
		this.selMenu.pop();
		
		tmpScooter.setx(x);
		tmpScooter.sety(y);
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

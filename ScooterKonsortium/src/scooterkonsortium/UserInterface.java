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
		String[]   entries1   = new String[] {"Go to Operative mode", "Go to Setup mode"};
		char[]     controls1  = new char[] {'O', 'S'};
		Runnable[] functions1 = new Runnable[] {()->selMenu="operative", ()->selMenu="setup"};
		menus.createMenu("main", entries1, controls1, functions1);
		
		String[]   entries2   = new String[] {"Go to Operative mode"};
		char[]     controls2  = new char[] {'O'};
		Runnable[] functions2 = new Runnable[] {()->selMenu="operative"};
		menus.createMenu("setup", entries2, controls2, functions2);
		
		String[]   entries3   = new String[] {"Go to Setup Mode"};
		char[]     controls3  = new char[] {'S'};
		Runnable[] functions3 = new Runnable[] {()->selMenu="setup"};
		menus.createMenu("operative", entries3, controls3, functions3);
	}


	public void showMenu() {
		menus.drawUserInterface(selMenu);
	}
	
	//Setup Mode:
	Ladepunkt testLadepunkt;
	Firma testFirma;
	public void createLadepunkt(String psName, Koordinaten poFixKoord, int piLadeCap, int piCurrentUse) {
		testLadepunkt = new Ladepunkt(psName,poFixKoord,piLadeCap,piCurrentUse);
	}
	public void createFirma(String psNameFirma, double pdKostenJeFahrt, String psAdresse, int piPLZ,String psStadt, String psHotline) {
		testFirma = new Firma(psNameFirma, pdKostenJeFahrt, psAdresse, piPLZ, psStadt, psHotline);
	}
	public void createScooter() {
		
	}
	
	
}

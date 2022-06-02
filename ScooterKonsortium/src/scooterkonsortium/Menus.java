package scooterkonsortium;

import konsortiumdata.Firma;
import konsortiumdata.KonsortiumData;
import konsortiumdata.Ladepunkt;
import konsortiumdata.Scooter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Menus {
	private HashMap<String, Menu> menus = new HashMap<>();
	private int width = 10;
	
	private Stack<Object> showObject = new Stack<>();
	private KonsortiumData oData;
	
	private Scanner sc;
	
	public Menus(KonsortiumData oData, Scanner sc) {
		this.showObject.push(null); //Lowest item is null if data should be shown
		this.oData = oData;
		this.sc = new Scanner(System.in);
	}
	
	public void drawUserInterface(String menuName) {
		if(!menus.containsKey(menuName)) {
			System.err.println("Menu not found");
			return;
		}
		
		//grab current menu
		Menu m = menus.get(menuName);
		
		//construct necessary elements
		StringBuilder sb = new StringBuilder();
		String menuTitle = this.createTitle(menuName);
		// Data of Companies Scooters and Ladepunkte already stored in the program
		String[] asData = this.getKonsortiumData();
		// Data to be show to the user above the menu to visualize menu entries
		String[] sShowData = getShowData();
		
		
		
		
		//Recalculate new width of data to be shown
		if(this.showObject.peek() != null) {
			int t = 0;
			for(String s : sShowData) {
				t = Math.max(s.length(), t);
				}
			this.width = Math.max(t, this.width);
		}
		
		//Recalculate new width of data to be shown
		if(asData.length>0) {
			int t = 0;
			for(String s : asData) {
				t = Math.max(s.length(), t);
			}
			this.width = Math.max(t, this.width);
		}
		
		
		//Draw top Line
		sb.append("+");
		for (int i=0;i<this.width+6;i++) sb.append("-");
		sb.append("+\n");
		sb.replace(4,menuTitle.length()+4, menuTitle);

		
		String line;
		
		//Draw KosnortiumData as List
		if(asData.length>0) {
			for (String dataLine : asData) {
				line = String.format("| %-" + (width + 4) + "s |%n", dataLine);
				sb.append(line);
			}
			sb.append("+");
			for (int i=0;i<this.width+6;i++) sb.append("-");
			sb.append("+\n");
		}
		
		//Draw Data to be shown
		if(this.showObject.peek() != null) {
			for (String dataLine : sShowData) {
				line = String.format("| %-" + (width + 4) + "s |%n", dataLine);
				sb.append(line);
			}
			
			sb.append("+");
			for (int i=0;i<this.width+6;i++) sb.append("-");
			sb.append("+\n");
		}
		
		//Draw Menu
		for (String menuLine : m.getMenu(width)) {
			line = String.format("| %s |%n", menuLine);
			sb.append(line);
		}
		
		sb.append("+");
		for (int i=0;i<this.width+6;i++) sb.append("-");
		sb.append("+\n");
		
		System.out.print(sb);
		m.waitForAction(this.sc);
	}
	
	private String[] getKonsortiumData() {
		ArrayList<String> lines = new ArrayList<>(); 
		
		String[] asFirmenNames = oData.getFirmaNames();
		ArrayList<Firma> alFirmen = new ArrayList<Firma>();
		for(String key : asFirmenNames) {
			alFirmen.add(this.oData.getFirma(key));
		}
		
		// go through all companies
		Scooter[] scooters;
		Ladepunkt[] ladepunkte;
		
		// Iterate through all Companies
		for(int iFirma=0;iFirma<alFirmen.size();iFirma++) {
			lines.add(String.format("+---%s", alFirmen.get(iFirma).getName()));
			
			// Normal loop
			if(iFirma != alFirmen.size()-1) {
				// All scooters of company
				scooters = alFirmen.get(iFirma).getScooters();
				
				// Iterate through every Scooter
				lines.add("|   +---Scooters");
				for(int iScooter=0;iScooter<scooters.length;iScooter++) {
					lines.add(String.format("|   |   +---Scooter at %d %d", scooters[iScooter].x, scooters[iScooter].y));
				}
				
				ladepunkte = alFirmen.get(iFirma).getladepunkte();
				// Iterate through every Ladepunkt
				lines.add("|   +---Ladepunkte");
				for(int iLadepunkt=0;iLadepunkt<ladepunkte.length;iLadepunkt++) {
					lines.add(String.format("|       +---%s at %d %d", ladepunkte[iLadepunkt].getNameLadepunkt(), ladepunkte[iLadepunkt].x, ladepunkte[iLadepunkt].y));
				}

			} else { // Only on last element
				// All scooters of company
				scooters = alFirmen.get(iFirma).getScooters();
				
				// Iterate through every Scooter
				lines.add("    +---Scooters");
				for(int iScooter=0;iScooter<scooters.length;iScooter++) {
					lines.add(String.format("    |   +---Scooter at %d %d", scooters[iScooter].x, scooters[iScooter].y));
				}
				
				ladepunkte = alFirmen.get(iFirma).getladepunkte();
				// Iterate through every Ladepunkt
				lines.add("    +---Ladepunkte");
				for(int iLadepunkt=0;iLadepunkt<ladepunkte.length;iLadepunkt++) {
					lines.add(String.format("        +---%s at %d %d", ladepunkte[iLadepunkt].getNameLadepunkt(), ladepunkte[iLadepunkt].x, ladepunkte[iLadepunkt].y));
				}
			}
			
			
		}
		
		
		String out[] = new String[lines.size()];
		return lines.toArray(out);
	}
	
	
	private String[] getShowData() {
		if(this.showObject.peek() == null) return null;
		ArrayList<String> lines = new ArrayList<>(); 
		
		this.showObject.peek().toString().lines().forEach((l)-> lines.add(l));
		
		String out[] = new String[lines.size()];
		return lines.toArray(out);
	}
	
	public void createMenu(String name, String[] entries, char[] controls, Runnable[] functions) {
		if (!(entries.length == controls.length && controls.length == functions.length)) {
			System.err.printf("The %s menu is inconsistant! Some rows are missing a function, controls or an entiry%n", name);
		}
		
		Menu oM = new Menu(entries, controls, functions);
		menus.put(name, oM);
		this.width = Math.max(this.width, Math.max(oM.getWidth(), createTitle(name).length()));
	}
	
	public void createMenu(String name, String[] entries, char[] controls, Runnable[] functions, String toolTip) {
		if (!(entries.length == controls.length && controls.length == functions.length)) {
			System.err.printf("The %s menu is inconsistant! Some rows are missing a function, controls or an entiry%n", name);
		}
		
		Menu oM = new Menu(entries, controls, functions, toolTip);
		menus.put(name, oM);
		this.width = Math.max(this.width, Math.max(oM.getWidth(), createTitle(name).length()));
	}
	
	private String createTitle(String menuName) {
		return String.format("+ %s menu +", menuName);
	}

	public void popShowData() {
		this.showObject.pop();
	}
	
	public void pushShowData(Object oShowData) {
		this.showObject.push(oShowData);	
	}
}

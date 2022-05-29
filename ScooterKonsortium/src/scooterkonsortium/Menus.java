package scooterkonsortium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Menus {
	private HashMap<String, Menu> menus = new HashMap<>();
	private int width = 10;
	
	private Object showObject;
	
	private Scanner sc;
	
	public Menus(Scanner sc) {
		this.showObject = null;
		this.sc = new Scanner(System.in);
	}
	
	public void drawUserInterface(String menuName) {
		if(!menus.containsKey(menuName)) {
			System.err.println("Menu not found");
			return;
		}
		
		Menu m = menus.get(menuName);
		
		
		StringBuilder sb = new StringBuilder();
		String menuTitle = this.createTitle(menuName);
		String[] sData = getData();
		if(this.showObject != null) {
			int t = 0;
			for(String s : sData) {
				t = Math.max(s.length(), t);
			}
			this.width = Math.max(t, this.width);
		}
		
		sb.append("+");
		for (int i=0;i<this.width+6;i++) sb.append("-");
		sb.append("+\n");
		sb.replace(4,menuTitle.length()+4, menuTitle);

		String line;
		
		if(this.showObject != null) {
			for (String dataLine : sData) {
				line = String.format("| %-" + (width + 4) + "s |%n", dataLine);
				sb.append(line);
			}
			
			sb.append("+");
			for (int i=0;i<this.width+6;i++) sb.append("-");
			sb.append("+\n");
		}
		
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
	
	private String[] getData() {
		if(this.showObject == null) return null;
		ArrayList<String> lines = new ArrayList<>(); 
		
		this.showObject.toString().lines().forEach((l)-> lines.add(l));
		
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

	public void setShowData(Object oShowData) {
		this.showObject = oShowData;	
	}
}

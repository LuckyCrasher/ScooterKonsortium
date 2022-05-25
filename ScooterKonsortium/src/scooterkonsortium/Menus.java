package scooterkonsortium;

import java.util.HashMap;
import java.util.Scanner;

public class Menus {
	private HashMap<String, Menu> menus = new HashMap<>();
	private int width = 10;
	
	private Scanner sc;
	
	public Menus(Scanner sc) {
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
		
		sb.append("+");
		for (int i=0;i<this.width+6;i++) sb.append("-");
		sb.append("+\n");
		sb.replace(4,menuTitle.length()+4, menuTitle);

		String line;
		
		for (String dataLine : m.getMenuData(width)) {
			line = String.format("| %s |%n", dataLine);
			sb.append(line);
		}
		if(m.hasData() ) {
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
	
	public void createMenu(String name, UserInterface ui, String[] entries, char[] controls, Runnable[] functions) {
		if (!(entries.length == controls.length && controls.length == functions.length)) {
			System.err.println("The menu is inconsistant! Some rows are missing a function, controls or an entiry");
		}
		
		Menu oM = new Menu(ui, entries, controls, functions);
		menus.put(name, oM);
		this.width = Math.max(this.width, Math.max(oM.getWidth(), createTitle(name).length()));
	}
	
	public void createMenu(String name, UserInterface ui, String[] entries, char[] controls, Runnable[] functions, String toolTip) {
		if (!(entries.length == controls.length && controls.length == functions.length)) {
			System.err.println("The menu is inconsistant! Some rows are missing a function, controls or an entiry");
		}
		
		Menu oM = new Menu(ui, entries, controls, functions, toolTip);
		menus.put(name, oM);
		this.width = Math.max(this.width, Math.max(oM.getWidth(), createTitle(name).length()));
	}
	
	private String createTitle(String menuName) {
		return String.format("+ %s menu +", menuName);
	}
}

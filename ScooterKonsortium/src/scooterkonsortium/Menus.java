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
		
		sb.append(m.getMenu(width));
		
		System.out.print(sb);
		m.waitForAction(this.sc);
	}
	
	public void createMenu(String name, String[] entries, char[] controls, Runnable[] functions) {
		if (entries.length != controls.length && controls.length != functions.length) {
			System.err.println("The menu is inconsistant! Some rows are missing a function, controls or an entiry");
		}
		
		Menu oM = new Menu(entries, controls, functions);
		menus.put(name, oM);
		this.width = Math.max(this.width, oM.getWidth());
	}
}

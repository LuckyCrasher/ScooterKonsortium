package scooterkonsortium;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

private UserInterface ui;
	
	private String[] entries;
	private char[] controls;
	private Runnable[] functions;
	private String toolTip = "";
	private int width = 10;
	private Object oData;
	
	public Menu(UserInterface ui, String[] entries, char[] controls, Runnable[] functions) {
		if (entries.length != controls.length && controls.length != functions.length) {
			System.err.println("The menu is inconsistant! Some rows are missing a function, controls or an entiry");
		}
		this.ui = ui;
		this.entries = entries;
		this.controls = controls;
		this.functions = functions;
		calcWidth();
	}
	
	public Menu(UserInterface ui, String[] entries, char[] controls, Runnable[] functions, String toolTip) {
		if (entries.length != controls.length && controls.length != functions.length) {
			System.err.println("The menu is inconsistant! Some rows are missing a function, controls or an entiry");
		}
		
		this.ui = ui;
		this.entries = entries;
		this.controls = controls;
		this.functions = functions;
		this.toolTip = toolTip;
		calcWidth();
	}

	private void calcWidth() {
		int maxLen = 0;
		for (String entry : this.entries) {
			maxLen = Math.max(maxLen, entry.length());
		}
		this.width = maxLen;
	}

	public void waitForAction(Scanner sc) {
		System.out.printf("%s ->", toolTip);
		String input = sc.nextLine();
		if(input.length()< 1) {
			return;
		}
		char cntrl = input.toUpperCase().charAt(0);
		String controls = new String(this.controls);
		int fn = controls.indexOf(cntrl);
		if (fn < 0) {
			System.err.println("Matching Function not found");
			return;
		}
		this.functions[fn].run();
	}

	public String[] getMenu(int spacing) {
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		
		for (int i = 0; i < this.entries.length; i++) {
			line = String.format("%-" + spacing + "s   %s", this.entries[i], this.controls[i]);
			lines.add(line);
		}

		String out[] = new String[lines.size()];
		return lines.toArray(out);
	}

	public int getWidth() {
		return this.width;
	}

	public String[] getMenuData(int spacing) {
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		
		if(this.oData != null) {
			this.oData.toString().lines().forEach((l)-> lines.add(String.format("%-" + spacing + "s    ", l)));
		}
		
		String out[] = new String[lines.size()];
		return lines.toArray(out);
	}

	public boolean hasData() {
		return this.oData!=null;
	}

}

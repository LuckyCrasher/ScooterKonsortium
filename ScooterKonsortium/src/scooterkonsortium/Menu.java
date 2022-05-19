package scooterkonsortium;
import java.util.Scanner;

public class Menu {

	private String[] entries;
	private char[] controls;
	private Runnable[] functions;
	private String toolTip = "";
	private int width = 10;
	
	public Menu(String[] entries, char[] controls, Runnable[] functions) {
		if (entries.length != controls.length && controls.length != functions.length) {
			System.err.println("The menu is inconsistant! Some rows are missing a function, controls or an entiry");
		}
		this.entries = entries;
		this.controls = controls;
		this.functions = functions;
		calcWidth();
	}
	
	public Menu(String[] entries, char[] controls, Runnable[] functions, String toolTip) {
		if (entries.length != controls.length && controls.length != functions.length) {
			System.err.println("The menu is inconsistant! Some rows are missing a function, controls or an entiry");
		}
		this.entries = entries;
		this.controls = controls;
		this.functions = functions;
		this.toolTip = toolTip;
		calcWidth();
	}
	
	private void calcWidth() {
		int maxLen = 0;
		for(String entry : this.entries) {
			maxLen = Math.max(maxLen, entry.length());
		}
		this.width = maxLen;
	}
	
	public void waitForAction(Scanner sc) {
		System.out.printf("%s ->", toolTip);
		String input    = sc.nextLine();
		char   cntrl    = input.toUpperCase().charAt(0);
		String controls = new String(this.controls);
		int    fn       = controls.indexOf(cntrl);
		if(fn<0) {
			System.err.println("Matching Function not found");
			return;
		}
		this.functions[fn].run();
	}
	
	public String getMenu(int spacing) {
		StringBuilder sb = new StringBuilder();
		String line;
		for(int i=0;i<this.entries.length;i++) {
			line = String.format("%-" + spacing + "s   %s%n", this.entries[i], this.controls[i]);
			sb.append(line);
		}
		
		return sb.toString();
	}
	
	public int getWidth() {
		return this.width;
	}
	
}

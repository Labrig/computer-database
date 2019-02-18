package fr.excilys.view;

import java.util.Scanner;

public class CliView {

	private Scanner sc = new Scanner(System.in);
	
	public CliView() { }
	
	public void displayHelp() {
		System.out.println("Command :");
		System.out.println("-> /l company (list of the company)");
		System.out.println("-> /l computer (list of the computer)");
		System.out.println("-> /f computer (find a computer computer)");
		System.out.println("-> /c computer (create a computer)");
		System.out.println("-> /d computer (delete a computer)");
		System.out.println("-> /u computer (update a computer)");
		System.out.println();
		System.out.println("Computer options :");
		System.out.println("* id (the id of a computer)");
		System.out.println("* name (the name of a computer)");
		System.out.println("* intro (the introduction date of a computer, YYYY-MM-DD format)");
		System.out.println("* disco (the discontinued date of a computer, YYYY-MM-DD format)");
		System.out.println("* idCompany (the id of the company that product a computer");
	}
	
	public void displayHelpCommand() {
		System.out.println("/h if you need help");
	}
	
	public void displayResultCommand(String result) {
		System.out.println(result);
	}
	
	public String requestAttribute(String attribute) {
		System.out.print("Enter the computer "+attribute+" : ");
		return this.sc.nextLine();
	}
	
	public String waitForNewCommand() {
		return this.sc.nextLine();
	}
}

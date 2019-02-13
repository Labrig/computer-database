package fr.excilys.ui;

import java.util.Scanner;

import fr.excilys.exceptions.NotCommandeException;

/**
 * Class for running the application in CLI
 * @author Matheo
 *
 */
public class Main {

	public static void main(String[] args) {
		boolean isRunning = true;
		Scanner sc = new Scanner(System.in);
		while(isRunning) {
		    try {
		    	System.out.println("/h if you need help");
				Command.execute(sc.nextLine());
			} catch (NotCommandeException e) {
				System.out.println(e.getMessage());
			}
		    System.out.println("\n");
		}
		sc.close();
	}

}

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
		Command command = new Command(sc);
		while(isRunning) {
		    try {
		    	System.out.println("/h if you need help");
		    	String message = sc.nextLine();
		    	if(message.equals("exit")) {
		    		isRunning = false;
		    	} else {
		    		System.out.println(command.execute(message));
		    	}
			} catch (NotCommandeException e) {
				System.out.println(e.getMessage());
			}
		    System.out.println("\n");
		}
		System.out.println("Goodbye");
		sc.close();
	}

}

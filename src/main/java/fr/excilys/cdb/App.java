package fr.excilys.cdb;

import fr.excilys.controller.Controller;
import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.exceptions.DTOException;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.view.CliView;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main( String[] args ) {
    	boolean isRunning = true;
		Controller command = new Controller(new CliView());
		while(isRunning) {
			command.getView().displayHelpCommand();
		    try {
		    	String message = command.getView().waitForNewCommand();
		    	if(message.equals("exit")) {
		    		isRunning = false;
		    	} else {
		    		command.executeCommand(message);
		    	}
			} catch (NotCommandeException | DTOException | ComputerFormatException e) {
				System.out.println(e.getMessage());
			}
		    System.out.println("\n");
		}
		System.out.println("Goodbye");
    }
}

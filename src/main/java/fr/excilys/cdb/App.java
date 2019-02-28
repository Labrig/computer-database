package fr.excilys.cdb;

import fr.excilys.controller.Controller;
import fr.excilys.exceptions.ValidationException;
import fr.excilys.exceptions.MappingException;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.view.CliView;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main( String[] args ) {
    	boolean isRunning = true;
		Controller controller = new Controller(new CliView());
		while(isRunning) {
			controller.getView().displayHelpCommand();
		    try {
		    	String message = controller.getView().waitForNewCommand();
		    	if(message.equals("exit")) {
		    		isRunning = false;
		    	} else {
		    		controller.executeCommand(message);
		    	}
			} catch (NotCommandeException | MappingException | ValidationException e) {
				System.out.println(e.getMessage());
			}
		    System.out.println("\n");
		}
		System.out.println("Goodbye");
    }
}

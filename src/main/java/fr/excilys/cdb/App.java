package fr.excilys.cdb;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.config.SpringConfiguration;
import fr.excilys.controller.CliController;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.exceptions.mapping.MappingException;
import fr.excilys.exceptions.validation.ComputerValidationException;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main( String[] args ) {
    	boolean isRunning = true;
    	@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);

		CliController controller = vApplicationContext.getBean("cliController", CliController.class);
		while(isRunning) {
			controller.getView().displayHelpCommand();
		    try {
		    	String message = controller.getView().waitForNewCommand();
		    	if(message.equals("exit")) {
		    		isRunning = false;
		    	} else {
		    		controller.executeCommand(message);
		    	}
			} catch (NotCommandeException | MappingException | ComputerValidationException e) {
				System.out.println(e.getMessage());
			}
		    System.out.println("\n");
		}
		System.out.println("Goodbye");
    }
}

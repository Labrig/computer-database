package fr.excilys.console;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.config.SpringConsoleConfiguration;
import fr.excilys.controller.CliController;
import fr.excilys.exception.NotCommandeException;
import fr.excilys.exception.MappingException;
import fr.excilys.exception.ComputerValidationException;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main( String[] args ) {
    	boolean isRunning = true;
    	@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringConsoleConfiguration.class);

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

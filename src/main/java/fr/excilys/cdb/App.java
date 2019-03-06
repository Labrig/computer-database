package fr.excilys.cdb;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.excilys.controller.CliController;
import fr.excilys.exceptions.ValidationException;
import fr.excilys.exceptions.MappingException;
import fr.excilys.exceptions.NotCommandeException;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main( String[] args ) {
    	boolean isRunning = true;
    	@SuppressWarnings("resource")
		ApplicationContext vApplicationContext
	        = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");

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
			} catch (NotCommandeException | MappingException | ValidationException e) {
				System.out.println(e.getMessage());
			}
		    System.out.println("\n");
		}
		System.out.println("Goodbye");
    }
}

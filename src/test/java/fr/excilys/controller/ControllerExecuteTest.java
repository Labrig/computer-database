package fr.excilys.controller;

import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.config.SpringTestConfiguration;
import fr.excilys.controller.CliController;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.exceptions.mapping.MappingException;
import fr.excilys.exceptions.validation.ComputerValidationException;

@RunWith(Parameterized.class)
public class ControllerExecuteTest {

	private static CliController controller;
	
	private String message;
	
	public ControllerExecuteTest(String message) {
		this.message = message;
	}
	
	/**
	 * Création du jeu de test. 
	 * 
	 * @return l'ensemble des données de test. 
	 */
	@Parameters(name = "dt[{index}] : {0}") 
    public static Collection<Object[]> dt() {
        Object[][] data = new Object[][] { 
        	{null},
        	{""},
    		{"/l"},
    		{"/c"},
    		{"/f"},
    		{"/u"},
    		{"/d"},
    		{"/c company"},
    		{"/f company"},
    		{"/u company"},
    		{"test"},
    		{"/t"},
    		{"/f  computer"},
    		{"/computer"},
    		{"/t computer"},
    		{"/f computer 30"}
        };
        return Arrays.asList(data);
    }
	
	@BeforeClass
	public static void setUpBeforeClass() {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringTestConfiguration.class);
		controller = vApplicationContext.getBean("cliController", CliController.class);
	}
	
    @Test(expected = NotCommandeException.class)
	public void testExecuteException() throws NotCommandeException, ComputerValidationException, MappingException {
		controller.executeCommand(this.message);
	}

}

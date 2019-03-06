package fr.excilys.controller;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import fr.excilys.controller.CliController;
import fr.excilys.exceptions.ValidationException;
import fr.excilys.exceptions.MappingException;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.view.CliView;

@RunWith(Parameterized.class)
public class ControllerExecuteTest {

	private CliController command = new CliController(new CliView());
	
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
    		{"/d company"},
    		{"test"},
    		{"/t"},
    		{"/f  computer"},
    		{"/fcomputer"}
        };
        return Arrays.asList(data);
    }
	
    @Test(expected = NotCommandeException.class)
	public void testExecuteException() throws NotCommandeException, ValidationException, MappingException {
		this.command.executeCommand(this.message);
	}

}

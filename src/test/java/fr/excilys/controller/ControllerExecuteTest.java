package fr.excilys.controller;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import fr.excilys.controller.Controller;
import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.exceptions.DTOException;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.view.CliView;

@RunWith(Parameterized.class)
public class ControllerExecuteTest {

	private Controller command = new Controller(new CliView());
	
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
	public void testExecuteException() throws NotCommandeException, ComputerFormatException, DTOException {
		this.command.executeCommand(this.message);
	}

}

package fr.excilys.ui;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import fr.excilys.exceptions.NotCommandeException;

@RunWith(Parameterized.class)
public class CommandExecuteTest {

	private Command command = new Command(new Scanner(System.in));
	
	private String message;
	
	public CommandExecuteTest(String message) {
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
	public void testExecuteException() throws NotCommandeException {
		this.command.execute(this.message);
	}

}

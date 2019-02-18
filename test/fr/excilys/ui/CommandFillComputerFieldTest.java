package fr.excilys.ui;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import fr.excilys.exceptions.NotCommandeException;

@RunWith(Parameterized.class)
public class CommandFillComputerFieldTest {

	private Command command = new Command(new Scanner(System.in));
	
	private String attribute;
	
	public CommandFillComputerFieldTest(String attribute) {
		this.attribute = attribute;
	}
	
	/**
	 * Création du jeu de test. 
	 * 
	 * @return l'ensemble des données de test. 
	 */
	@Parameters(name = "dt[{index}] : {0}") 
    public static Collection<Object[]> dt() {
        Object[][] data = new Object[][] { 
        	{"test"},
    		{"id"},
    		{"intro"},
    		{"disco"},
    		{"idCompany"}
        };
        return Arrays.asList(data);
    }
    
    @Test(expected = NotCommandeException.class)
    public void testFillComputerFieldWithNoExistingField() throws NotCommandeException {
    	String[] attributes = {this.attribute};
    	System.setIn(new ByteArrayInputStream("test".getBytes()));
    	this.command.fillComputerField(attributes);
    }

}
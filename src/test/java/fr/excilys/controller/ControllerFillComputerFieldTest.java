package fr.excilys.controller;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import fr.excilys.controller.Controller;
import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.view.CliView;

@RunWith(Parameterized.class)
public class ControllerFillComputerFieldTest {

	private static CliView view = new CliView();
	private static Controller controller;
	private static Field scannerField;
	
	private String attribute;
	
	public ControllerFillComputerFieldTest(String attribute) {
		this.attribute = attribute;
	}
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			scannerField = CliView.class.getDeclaredField("sc");
			scannerField.setAccessible(true);
			scannerField.set(view, new Scanner(new ByteArrayInputStream("test".getBytes())));
			controller = new Controller(view);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
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
    public void testFillComputerFieldWithNoExistingField() throws NotCommandeException, ComputerFormatException {
    	String[] attributes = {this.attribute};
    	controller.fillComputerField(attributes);
    }

}
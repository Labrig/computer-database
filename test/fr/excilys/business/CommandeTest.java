package fr.excilys.business;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.ui.Command;

@RunWith(Parameterized.class)
public class CommandeTest {

	private String command;
	
	public CommandeTest(String command) {
		this.command = command;
	}
	
	@Parameters(name = "dt[{index}] : {0}") 
    public static Collection<Object[]> dt() {
        Object[][] data = new Object[][] { 
        	{""},
        	{null},
        	{" "},
        	{"="},
        	{"l company"},
        	{"lcompany "},
        	{"/c company id=1 name=test"},
        	{"/d company id=1 name=test"},
        	{"/u company id=1 name=test2"},
        	{"/r company id=1 name=test"},
        	{"computer"},
        	{"/c computer name=test intro=2019-06-19"},
        	{"/c computer nade=test"},
        	{"/c car name=test"},
        	{"/z computer name=test"},
        	{"/c computer id=1name=test"},
        	{"/c computer id=test name=test"}
        };
        return Arrays.asList(data);
    }
	
	@Test(expected = NotCommandeException.class)
	public void testException() throws NotCommandeException {
		Command.execute(this.command);
	}

}

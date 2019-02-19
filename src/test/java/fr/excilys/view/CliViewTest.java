package fr.excilys.view;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CliViewTest {

	private static CliView view = new CliView();
	private static Field scannerField;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			scannerField = CliView.class.getDeclaredField("sc");
			scannerField.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void setUp() {
		try {
			scannerField.set(view, new Scanner(new ByteArrayInputStream("test".getBytes())));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRequestAttribute() {
		assertEquals("test", view.requestAttribute("test"));
	}
	
	@Test
	public void testWaitForNewCommand() {
		assertEquals("test", view.waitForNewCommand());
	}
	
	@Test
	public void testDisplay() {
		view.displayHelpCommand();
		view.displayHelp();
		view.displayResultCommand("test");
	}

}

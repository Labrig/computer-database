package fr.excilys.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.config.SpringTestConfiguration;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.exceptions.mapping.MappingException;
import fr.excilys.exceptions.validation.ComputerValidationException;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.view.CliView;

public class ControllerTest {

	private static CliController controller;
	
	private CliView mockedView = mock(CliView.class);

	private static Field viewField;
	
	@BeforeClass
	public static void setUpBeforeClass() throws NoSuchFieldException, SecurityException {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringTestConfiguration.class);
		controller = vApplicationContext.getBean("cliController", CliController.class);
		
		viewField = CliController.class.getDeclaredField("view");
		viewField.setAccessible(true);
	}
	
	@Test
	public void testFillComputerFieldFull() throws ParseException, NotCommandeException, ComputerValidationException, MappingException, IllegalArgumentException, IllegalAccessException {
		when(mockedView.requestAttribute("id")).thenReturn("1");
		when(mockedView.requestAttribute("name")).thenReturn("test");
		when(mockedView.requestAttribute("intro")).thenReturn("2011-10-19");
		when(mockedView.requestAttribute("disco")).thenReturn("2019-02-18");
		Computer computer = new Computer();
		computer.setId(new Long(1));
		computer.setName("test");
		computer.setIntroduced(new SimpleDateFormat("yyyy-MM-dd").parse("2011-10-19"));
		computer.setDiscontinued(new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-18"));
		computer.setCompany(new Company());
		viewField.set(controller, mockedView);
		String[] attributes = {"id", "name", "intro", "disco"};
		Computer computerFill = controller.fillComputerField(attributes);
		assertEquals(computer, computerFill);
	}
	
}

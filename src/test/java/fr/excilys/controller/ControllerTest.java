package fr.excilys.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.exceptions.DTOException;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.model.Computer;
import fr.excilys.view.CliView;

public class ControllerTest {

	private CliView mockedView = mock(CliView.class);

	@Test
	public void testFillComputerFieldFull() throws ParseException, NotCommandeException, ComputerFormatException, DTOException {
		when(mockedView.requestAttribute("id")).thenReturn("1");
		when(mockedView.requestAttribute("name")).thenReturn("test");
		when(mockedView.requestAttribute("intro")).thenReturn("19-10-2011");
		when(mockedView.requestAttribute("disco")).thenReturn("18-02-2019");
		Computer computer = new Computer();
		computer.setId(new Long(1));
		computer.setName("test");
		computer.setIntroduced(new SimpleDateFormat("dd-MM-yyyy").parse("19-10-2011"));
		computer.setDiscontinued(new SimpleDateFormat("dd-MM-yyyy").parse("18-02-2019"));
		Controller controller = new Controller(mockedView);
		String[] attributes = {"id", "name", "intro", "disco"};
		Computer computerFill = controller.fillComputerField(attributes);
		assertEquals(computer, computerFill);
	}
	
}

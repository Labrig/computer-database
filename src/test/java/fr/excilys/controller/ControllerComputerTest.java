package fr.excilys.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doCallRealMethod;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.exceptions.DTOException;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;
import fr.excilys.view.CliView;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ComputerService.class})
public class ControllerComputerTest {

	private CliView mockedView = mock(CliView.class);
	
	@Test
	public void testListComputer() throws SQLException {
		List<Computer> listComputer = new ArrayList<>();
		for(int i = 1; i < 4 ; i++) {
			Computer computer = new Computer();
			computer.setId(new Long(i));
			computer.setName("test"+i);
			listComputer.add(computer);
		}
		PowerMockito.mockStatic(ComputerService.class);
		ComputerService service = mock(ComputerService.class);
		when(ComputerService.getInstance()).thenReturn(service);
		when(service.list()).thenReturn(listComputer);
		Controller controller = new Controller(new CliView());
		try {
			controller.executeCommand("/l computer");
		} catch (NotCommandeException e) {
			e.printStackTrace();
		} catch (ComputerFormatException e) {
			e.printStackTrace();
		} catch (DTOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindComputer() throws SQLException {
		Computer computer = new Computer();
		computer.setId(new Long(1));
		PowerMockito.mockStatic(ComputerService.class);
		ComputerService service = mock(ComputerService.class);
		when(ComputerService.getInstance()).thenReturn(service);
		when(service.find(new Long(1))).thenReturn(computer);
		
		when(mockedView.requestAttribute("id")).thenReturn("1");
		
		Controller controller = new Controller(mockedView);
		try {
			controller.executeCommand("/f computer");
		} catch (NotCommandeException e) {
			e.printStackTrace();
		} catch (ComputerFormatException e) {
			e.printStackTrace();
		} catch (DTOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateComputer() throws NotCommandeException, ComputerFormatException, DTOException {
		Computer computer = new Computer();
		computer.setName("test");
		PowerMockito.mockStatic(ComputerService.class);
		ComputerService service = mock(ComputerService.class);
		when(ComputerService.getInstance()).thenReturn(service);
		
		Controller controllerMock = mock(Controller.class);
		String[] attributes = {"name","intro","disco","idCompany"};
		when(controllerMock.fillComputerField(attributes)).thenReturn(computer);
		doCallRealMethod().when(controllerMock).executeCommand("/c computer");
		doCallRealMethod().when(controllerMock).setView(mockedView);
		try {
			controllerMock.setView(mockedView);
			controllerMock.executeCommand("/c computer");
		} catch (NotCommandeException e) {
			e.printStackTrace();
		} catch (ComputerFormatException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateComputer() throws NotCommandeException, ComputerFormatException, DTOException {
		Computer computer = new Computer();
		computer.setId(new Long(1));
		computer.setName("test");
		PowerMockito.mockStatic(ComputerService.class);
		ComputerService service = mock(ComputerService.class);
		when(ComputerService.getInstance()).thenReturn(service);
		
		Controller controllerMock = mock(Controller.class);
		String[] attributes = {"id","name","intro","disco","idCompany"};
		when(controllerMock.fillComputerField(attributes)).thenReturn(computer);
		doCallRealMethod().when(controllerMock).executeCommand("/u computer");
		doCallRealMethod().when(controllerMock).setView(mockedView);
		try {
			controllerMock.setView(mockedView);
			controllerMock.executeCommand("/u computer");
		} catch (NotCommandeException e) {
			e.printStackTrace();
		} catch (ComputerFormatException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteComputer() throws NotCommandeException, ComputerFormatException, DTOException {
		Computer computer = new Computer();
		computer.setId(new Long(1));
		PowerMockito.mockStatic(ComputerService.class);
		ComputerService service = mock(ComputerService.class);
		when(ComputerService.getInstance()).thenReturn(service);
		
		Controller controllerMock = mock(Controller.class);
		String[] attributes = {"id"};
		when(controllerMock.fillComputerField(attributes)).thenReturn(computer);
		doCallRealMethod().when(controllerMock).executeCommand("/d computer");
		doCallRealMethod().when(controllerMock).setView(mockedView);
		try {
			controllerMock.setView(mockedView);
			controllerMock.executeCommand("/d computer");
		} catch (NotCommandeException e) {
			e.printStackTrace();
		} catch (ComputerFormatException e) {
			e.printStackTrace();
		}
	}

}

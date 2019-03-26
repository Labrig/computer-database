package fr.excilys.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doCallRealMethod;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.config.SpringBindingTestConfiguration;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.ServiceException;
import fr.excilys.exception.NotCommandeException;
import fr.excilys.exception.MappingException;
import fr.excilys.exception.ComputerValidationException;
import fr.excilys.service.ComputerService;
import fr.excilys.view.CliView;

public class ControllerComputerTest {

	private static CliController controller;
	
	private static CliController controllerMock = mock(CliController.class);
	
	private static Field computerServiceField;

	private static Field viewField;
	
	@BeforeClass
	public static void setUpBeforeClass() throws BeansException, IllegalAccessException, NoSuchFieldException, SecurityException {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringBindingTestConfiguration.class);
		controller = vApplicationContext.getBean("cliController", CliController.class);
		
		computerServiceField = CliController.class.getDeclaredField("computerService");
		computerServiceField.setAccessible(true);
		
		viewField = CliController.class.getDeclaredField("view");
		viewField.setAccessible(true);
		
		viewField.set(controllerMock, vApplicationContext.getBean("cliView", CliView.class));
	}
	
	@Test
	public void testListComputer() throws ServiceException, IllegalArgumentException, IllegalAccessException, ComputerValidationException, NotCommandeException, MappingException {
		List<ComputerDTO> listComputer = new ArrayList<>();
		for(int i = 1; i < 4 ; i++) {
			ComputerDTO computer = new ComputerDTO();
			computer.setId(String.valueOf(i));
			computer.setName("test"+i);
			listComputer.add(computer);
		}
		ComputerService service = mock(ComputerService.class);
		when(service.list()).thenReturn(listComputer);
		computerServiceField.set(controller, service);
		controller.executeCommand("/l computer");
		verify(service, times(1)).list();
	}
	
	@Test
	public void testFindComputer() throws ComputerValidationException, ServiceException, IllegalArgumentException, IllegalAccessException, NotCommandeException, MappingException {
		ComputerDTO computer = new ComputerDTO();
		computer.setId("1");
		ComputerService service = mock(ComputerService.class);
		when(service.find(new Long(1))).thenReturn(computer);
		
		CliView view = mock(CliView.class);
		when(view.requestAttribute("id")).thenReturn("1");
		
		computerServiceField.set(controller, service);
		viewField.set(controller, view);
		controller.executeCommand("/f computer");
		verify(view, times(1)).requestAttribute("id");
	}
	
	@Test
	public void testCreateComputer() throws NotCommandeException, ComputerValidationException, MappingException, IllegalArgumentException, IllegalAccessException {
		ComputerDTO computer = new ComputerDTO();
		computer.setName("test");
		ComputerService service = mock(ComputerService.class);
		
		String[] attributes = {"name","intro","disco","idCompany"};
		when(controllerMock.fillComputerField(attributes)).thenReturn(computer);
		doCallRealMethod().when(controllerMock).executeCommand("/c computer");
		computerServiceField.set(controllerMock, service);
		controllerMock.executeCommand("/c computer");
		
		verify(controllerMock, times(1)).fillComputerField(attributes);
	}
	
	@Test
	public void testUpdateComputer() throws NotCommandeException, ComputerValidationException, MappingException, IllegalArgumentException, IllegalAccessException {
		ComputerDTO computer = new ComputerDTO();
		computer.setId("1");
		computer.setName("test");
		ComputerService service = mock(ComputerService.class);
		
		String[] attributes = {"id","name","intro","disco","idCompany"};
		when(controllerMock.fillComputerField(attributes)).thenReturn(computer);
		doCallRealMethod().when(controllerMock).executeCommand("/u computer");
		computerServiceField.set(controllerMock, service);
		controllerMock.executeCommand("/u computer");
		
		verify(controllerMock, times(1)).fillComputerField(attributes);
	}
	
	@Test
	public void testDeleteComputer() throws NotCommandeException, ComputerValidationException, MappingException, IllegalArgumentException, IllegalAccessException {
		ComputerDTO computer = new ComputerDTO();
		computer.setId("1");
		ComputerService service = mock(ComputerService.class);
		
		String[] attributes = {"id"};
		when(controllerMock.fillComputerField(attributes)).thenReturn(computer);
		doCallRealMethod().when(controllerMock).executeCommand("/d computer");
		computerServiceField.set(controllerMock, service);
		controllerMock.executeCommand("/d computer");
		
		verify(controllerMock, times(1)).fillComputerField(attributes);
	}

}
package fr.excilys.controller;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.config.SpringBindingTestConfiguration;
import fr.excilys.dto.CompanyDTO;
import fr.excilys.exception.ServiceException;
import fr.excilys.exception.NotCommandeException;
import fr.excilys.exception.MappingException;
import fr.excilys.exception.ComputerValidationException;
import fr.excilys.service.CompanyService;
import fr.excilys.view.CliView;

public class ControllerCompanyTest {

	private static CliController controller;
	
	private static Field companyServiceField;
	
	private static CliController controllerMock = mock(CliController.class);
	
	private static Field viewField;
	
	@BeforeClass
	public static void setUpBeforeClass() throws BeansException, IllegalAccessException, NoSuchFieldException, SecurityException {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringBindingTestConfiguration.class);
		controller = vApplicationContext.getBean("cliController", CliController.class);
		
		companyServiceField = CliController.class.getDeclaredField("companyService");
		companyServiceField.setAccessible(true);
		
		viewField = CliController.class.getDeclaredField("view");
		viewField.setAccessible(true);
		
		viewField.set(controllerMock, vApplicationContext.getBean("cliView", CliView.class));
	}
	
	@Test
	public void testListCompany() throws ServiceException, ComputerValidationException, IllegalArgumentException, IllegalAccessException, NotCommandeException, MappingException {
		List<CompanyDTO> listCompany = new ArrayList<>();
		for(int i = 1; i < 4 ; i++) {
			CompanyDTO company = new CompanyDTO();
			company.setId(String.valueOf(i));
			company.setName("test"+i);
			listCompany.add(company);
		}
		CompanyService service = mock(CompanyService.class);
		when(service.list()).thenReturn(listCompany);
		companyServiceField.set(controller, service);
		controller.executeCommand("/l company");
		verify(service, times(1)).list();
	}
	
	@Test
	public void testDeleteCompany() throws NotCommandeException, MappingException, IllegalArgumentException, IllegalAccessException, ComputerValidationException, ServiceException {
		CompanyDTO company = new CompanyDTO();
		company.setId("1");
		CompanyService service = mock(CompanyService.class);
		
		String[] attributes = {"id"};
		when(controllerMock.fillCompanyField(attributes)).thenReturn(company);
		doCallRealMethod().when(controllerMock).executeCommand("/d company");
		companyServiceField.set(controllerMock, service);
		controllerMock.executeCommand("/d company");
		verify(controllerMock, times(1)).fillCompanyField(attributes);
	}

}

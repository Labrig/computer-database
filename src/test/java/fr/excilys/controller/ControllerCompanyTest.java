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

import fr.excilys.config.SpringTestConfiguration;
import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.exceptions.mapping.MappingException;
import fr.excilys.exceptions.validation.ComputerValidationException;
import fr.excilys.model.Company;
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
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringTestConfiguration.class);
		controller = vApplicationContext.getBean("cliController", CliController.class);
		
		companyServiceField = CliController.class.getDeclaredField("companyService");
		companyServiceField.setAccessible(true);
		
		viewField = CliController.class.getDeclaredField("view");
		viewField.setAccessible(true);
		
		viewField.set(controllerMock, vApplicationContext.getBean("cliView", CliView.class));
	}
	
	@Test
	public void testListCompany() throws DAOException, ComputerValidationException, IllegalArgumentException, IllegalAccessException, NotCommandeException, MappingException {
		List<Company> listCompany = new ArrayList<>();
		for(int i = 1; i < 4 ; i++) {
			Company company = new Company();
			company.setId(new Long(i));
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
	public void testDeleteCompany() throws NotCommandeException, MappingException, IllegalArgumentException, IllegalAccessException, ComputerValidationException, DAOException {
		Company company = new Company();
		company.setId(new Long(1));
		CompanyService service = mock(CompanyService.class);
		
		String[] attributes = {"id"};
		when(controllerMock.fillCompanyField(attributes)).thenReturn(company);
		doCallRealMethod().when(controllerMock).executeCommand("/d company");
		companyServiceField.set(controllerMock, service);
		controllerMock.executeCommand("/d company");
		verify(controllerMock, times(1)).fillCompanyField(attributes);
	}

}

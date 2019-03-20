package fr.excilys.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.config.SpringTestConfiguration;
import fr.excilys.dao.CompanyDAO;
import fr.excilys.exceptions.DAOException;
import fr.excilys.model.Company;
import fr.excilys.model.Company.CompanyBuilder;
import fr.excilys.service.CompanyService;

public class CompanyServiceTest {

	private static CompanyService service;
	
	private CompanyDAO mockedDAO = mock(CompanyDAO.class);

	private static Field daoField;
	
	@BeforeClass
	public static void setUpBeforeClass() throws NoSuchFieldException, SecurityException {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringTestConfiguration.class);
		service = vApplicationContext.getBean("companyService", CompanyService.class);
		
		daoField = CompanyService.class.getDeclaredField("companyDAO");
		daoField.setAccessible(true);
	}
	
	@Test
	public void testListCompany() throws DAOException, IllegalArgumentException, IllegalAccessException {
		List<Company> listCompanySet = new ArrayList<>();
		when(mockedDAO.findAll()).thenReturn(listCompanySet);
		
		daoField.set(service, mockedDAO);
		
		List<Company> listCompanyFound = service.list();
		assertEquals(listCompanySet, listCompanyFound);
	}
	
	@Test
	public void testFindCompany() throws DAOException, IllegalArgumentException, IllegalAccessException {
		Company companySet = new CompanyBuilder().setName("test").setId(1L).build();
		when(mockedDAO.getById(1L)).thenReturn(companySet);
		
		daoField.set(service, mockedDAO);
		
		Company companyFound = service.find(1L);
		assertEquals(companySet, companyFound);
	}

}

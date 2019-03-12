package fr.excilys.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.config.SpringTestConfiguration;
import fr.excilys.dao.ComputerDAO;
import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.validation.ComputerValidationException;
import fr.excilys.model.Computer;
import fr.excilys.model.Computer.ComputerBuilder;
import fr.excilys.service.ComputerService;

public class ComputerServiceTest {

	private static ComputerService service;
	
	private ComputerDAO mockedDAO = mock(ComputerDAO.class);

	private static Field daoField;
	
	@BeforeClass
	public static void setUpBeforeClass() throws NoSuchFieldException, SecurityException {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringTestConfiguration.class);
		service = vApplicationContext.getBean("computerService", ComputerService.class);
		
		daoField = ComputerService.class.getDeclaredField("dao");
		daoField.setAccessible(true);
	}
	
	@Test
	public void testListComputer() throws DAOException, IllegalArgumentException, IllegalAccessException {
		List<Computer> listComputerSet = new ArrayList<>();
		when(mockedDAO.list()).thenReturn(listComputerSet);
		
		daoField.set(service, mockedDAO);
		
		List<Computer> listComputerFound = service.list();
		assertEquals(listComputerSet, listComputerFound);
	}
	
	@Test
	public void testFindComputer() throws DAOException, ComputerValidationException, IllegalArgumentException, IllegalAccessException {
		Computer computerSet = new ComputerBuilder().setName("test").setId(1L).build();
		when(mockedDAO.find(1L)).thenReturn(computerSet);
		
		daoField.set(service, mockedDAO);
		
		Computer computerFound = service.find(1L);
		assertEquals(computerSet, computerFound);
	}
	
	@Test
	public void testCreateComputer() throws DAOException, ComputerValidationException, IllegalArgumentException, IllegalAccessException {
		Computer computerSet = new ComputerBuilder().setName("test").setId(1L).build();
		daoField.set(service, mockedDAO);
		service.create(computerSet);
		
		verify(mockedDAO, times(1)).insert(computerSet);
	}
	
	@Test
	public void testUpdateComputer() throws DAOException, ComputerValidationException, IllegalArgumentException, IllegalAccessException {
		Computer computerSet = new ComputerBuilder().setName("test").setId(1L).build();
		daoField.set(service, mockedDAO);
		service.update(computerSet);
		
		verify(mockedDAO, times(1)).update(computerSet);
	}
	
	@Test
	public void testDeleteComputer() throws DAOException, ComputerValidationException, IllegalArgumentException, IllegalAccessException {
		daoField.set(service, mockedDAO);
		service.delete(1L);
		
		verify(mockedDAO, times(1)).delete(1L);
	}
	
	@Test
	public void testCountComputer() throws DAOException, IllegalArgumentException, IllegalAccessException {
		when(mockedDAO.count()).thenReturn(3);
		
		daoField.set(service, mockedDAO);
		
		assertEquals(3, service.count());
	}
	
	@Test
	public void testByName() throws DAOException, IllegalArgumentException, IllegalAccessException {
		List<Computer> listComputerSet = new ArrayList<>();
		when(mockedDAO.listByName("test")).thenReturn(listComputerSet);
		
		daoField.set(service, mockedDAO);
		
		List<Computer> listComputerFound = service.listByName("test");
		assertEquals(listComputerSet, listComputerFound);
	}
	
	@Test
	public void testWithPagination() throws DAOException, IllegalArgumentException, IllegalAccessException {
		List<Computer> listComputerSet = new ArrayList<>();
		when(mockedDAO.listWithPagination(1, 5)).thenReturn(listComputerSet);
		
		daoField.set(service, mockedDAO);
		
		List<Computer> listComputerFound = service.listWithPagination(1, 5);
		assertEquals(listComputerSet, listComputerFound);
	}
	
	@Test
	public void testWithPaginationByName() throws DAOException, IllegalArgumentException, IllegalAccessException {
		List<Computer> listComputerSet = new ArrayList<>();
		when(mockedDAO.listByNameWithPagination("test", 1, 5)).thenReturn(listComputerSet);
		
		daoField.set(service, mockedDAO);
		
		List<Computer> listComputerFound = service.listByNameWithPagination("test", 1, 5);
		assertEquals(listComputerSet, listComputerFound);
	}
	
}
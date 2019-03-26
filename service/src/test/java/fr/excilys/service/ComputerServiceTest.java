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

import fr.excilys.config.SpringBindingTestConfiguration;
import fr.excilys.dao.ComputerDAO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exception.ServiceException;
import fr.excilys.exception.ComputerMappingException;
import fr.excilys.exception.ComputerValidationException;
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
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringBindingTestConfiguration.class);
		service = vApplicationContext.getBean("computerService", ComputerService.class);
		
		daoField = ComputerService.class.getDeclaredField("dao");
		daoField.setAccessible(true);
	}
	
	@Test
	public void testListComputer() throws ServiceException, IllegalArgumentException, IllegalAccessException {
		List<Computer> listComputerSet = new ArrayList<>();
		when(mockedDAO.findAll()).thenReturn(listComputerSet);
		
		daoField.set(service, mockedDAO);
		
		List<ComputerDTO> listComputerFound = service.list();
		assertEquals(new ArrayList<ComputerDTO>(), listComputerFound);
	}
	
	@Test
	public void testFindComputer() throws ServiceException, ComputerValidationException, IllegalArgumentException, IllegalAccessException {
		Computer computerSet = new ComputerBuilder().setName("test").setId(1L).build();
		when(mockedDAO.getById(1L)).thenReturn(computerSet);
		
		daoField.set(service, mockedDAO);
		
		ComputerDTO computerFound = service.find(1L);
		assertEquals(new ComputerDTOBuilder().setName("test").setId("test").build(), computerFound);
	}
	
	@Test
	public void testCreateComputer() throws ServiceException, ComputerValidationException, IllegalArgumentException, IllegalAccessException, ComputerMappingException {
		Computer computerSet = new ComputerBuilder().setName("test").setId(1L).build();
		daoField.set(service, mockedDAO);
		service.create(new ComputerDTOBuilder().setName("test").setId("1").build());
		
		verify(mockedDAO, times(1)).insert(computerSet);
	}
	
	@Test
	public void testUpdateComputer() throws ServiceException, ComputerValidationException, IllegalArgumentException, IllegalAccessException, ComputerMappingException {
		Computer computerSet = new ComputerBuilder().setName("test").setId(1L).build();
		daoField.set(service, mockedDAO);
		service.update(new ComputerDTOBuilder().setName("test").setId("1").build());
		
		verify(mockedDAO, times(1)).update(computerSet);
	}
	
	@Test
	public void testDeleteComputer() throws ServiceException, ComputerValidationException, IllegalArgumentException, IllegalAccessException {
		daoField.set(service, mockedDAO);
		service.delete(1L);
		
		verify(mockedDAO, times(1)).deleteById(1L);
	}
	
	@Test
	public void testCountComputer() throws ServiceException, IllegalArgumentException, IllegalAccessException {
		when(mockedDAO.count()).thenReturn(3);
		
		daoField.set(service, mockedDAO);
		
		assertEquals(3, service.count());
	}
	
	@Test
	public void testByName() throws ServiceException, IllegalArgumentException, IllegalAccessException {
		List<Computer> listComputerSet = new ArrayList<>();
		when(mockedDAO.findByNameContaining("test")).thenReturn(listComputerSet);
		
		daoField.set(service, mockedDAO);
		
		List<ComputerDTO> listComputerFound = service.listByName("test");
		assertEquals(new ArrayList<ComputerDTO>(), listComputerFound);
	}
	
	@Test
	public void testWithPagination() throws ServiceException, IllegalArgumentException, IllegalAccessException {
		List<Computer> listComputerSet = new ArrayList<>();
		when(mockedDAO.findAllWithPagination(1, 5)).thenReturn(listComputerSet);
		
		daoField.set(service, mockedDAO);
		
		List<ComputerDTO> listComputerFound = service.listWithPagination(1, 5);
		assertEquals(new ArrayList<ComputerDTO>(), listComputerFound);
	}
	
}
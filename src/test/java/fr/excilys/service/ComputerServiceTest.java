package fr.excilys.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;
import fr.excilys.service.ServiceFactory;

public class ComputerServiceTest {

//	private static ComputerService manager = ServiceFactory.getInstance().getComputerService();
//	private static Field entityManagerField;
//	
//	@BeforeClass
//	public static void setUpBeforeClass() {
//		try {
//			entityManagerField = ComputerService.class.getDeclaredField("entityManager");
//			entityManagerField.setAccessible(true);
//			entityManagerField.set(manager, Persistence.createEntityManagerFactory("computer-database-test").createEntityManager());
//		} catch (NoSuchFieldException | SecurityException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testListComputerEmpty() {
//		List<Computer> listComputer = manager.listComputer();
//		assertEquals(0, listComputer.size());
//	}
//	
//	@Test
//	public void testFindComputerEmpty() {
//		Computer computer = manager.findComputer(1);
//		assertNull(computer);
//	}
//	
//	@Test
//	public void testLCRUD() {
//		Computer computer = new Computer();
//		computer.setName("test");
//		manager.createComputer(computer);
//		List<Computer> listComputer = manager.listComputer();
//		assertEquals(1, listComputer.size());
//		computer = manager.findComputer(listComputer.get(0).getId());
//		assertEquals(listComputer.get(0), computer);
//		assertEquals("test", computer.getName());
//		Computer computerUpdate = new Computer();
//		computerUpdate.setId(computer.getId());
//		computerUpdate.setName("test2");
//		computer = manager.updateComputer(computerUpdate);
//		assertEquals("test2", computer.getName());
//		manager.deleteComputer(computer);
//		assertEquals(0, manager.listComputer().size());
//	}
	
}
package fr.excilys.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.zaxxer.hikari.HikariDataSource;

import fr.excilys.config.SpringTestConfiguration;
import fr.excilys.exceptions.DAOException;
import fr.excilys.model.Computer;
import fr.excilys.model.Computer.ComputerBuilder;

public class ComputerDAOTest {

	private static HikariDataSource dataSource;
	
	private static ComputerDAO computerDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws NoSuchFieldException, SecurityException {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringTestConfiguration.class);
		dataSource = vApplicationContext.getBean("dataSource", HikariDataSource.class);
		computerDAO = vApplicationContext.getBean("computerDAO", ComputerDAO.class);
	}
	
	@Before
	public void setUp() throws SQLException {
		try(Connection connect = dataSource.getConnection();
				PreparedStatement statement = connect.prepareStatement("DELETE FROM computer")) {
			statement.execute();
		}
	}
	
	@Test
	public void testFindAndListComputer() throws SQLException, DAOException {
		try(Connection connect = dataSource.getConnection();
				PreparedStatement statement = connect.prepareStatement("INSERT INTO computer (name) VALUES ('test')")) {
			assertEquals(0, computerDAO.count());
			assertEquals(0, computerDAO.list().size());
			statement.execute();
			assertEquals(1, computerDAO.count());
			List<Computer> list = computerDAO.list();
			assertEquals(1, list.size());
			Computer computer = computerDAO.find(list.get(0).getId());
			assertEquals(list.get(0), computer);
		}
	}
	
	@Test
	public void testListComputerWithPagination() throws SQLException, DAOException {
		try(Connection connect = dataSource.getConnection();
				PreparedStatement statement = connect.prepareStatement("INSERT INTO computer (name) VALUES (?)")) {
			for(int i = 1; i < 10; i++) {
				statement.setString(1, "test"+i);
				statement.execute();
			}
			assertEquals(9, computerDAO.count());
			List<Computer> list = computerDAO.listWithPagination(3, 3);
			assertEquals(3, list.size());
			assertEquals("test4", list.get(0).getName());
			assertEquals("test5", list.get(1).getName());
			assertEquals("test6", list.get(2).getName());
		}
	}
	
	@Test
	public void testListComputerByName() throws SQLException, DAOException {
		try(Connection connect = dataSource.getConnection();
				PreparedStatement statement = connect.prepareStatement("INSERT INTO computer (name) VALUES (?)")) {
			statement.setString(1, "test");
			statement.execute();
			statement.setString(1, "blablatest");
			statement.execute();
			statement.setString(1, "testblabla");
			statement.execute();
			statement.setString(1, "teblablast");
			statement.execute();
			statement.setString(1, "tset");
			statement.execute();
			statement.setString(1, "notFound");
			statement.execute();
			assertEquals(6, computerDAO.count());
			List<Computer> list = computerDAO.listByName("test");
			assertEquals(3, list.size());
		}
	}
	
	@Test
	public void testListComputerWithPaginationByName() throws SQLException, DAOException {
		try(Connection connect = dataSource.getConnection();
				PreparedStatement statement = connect.prepareStatement("INSERT INTO computer (name) VALUES (?)")) {
			for(int i = 1; i < 10; i++) {
				statement.setString(1, "test"+i);
				statement.execute();
				statement.setString(1, "tset"+i);
				statement.execute();
			}
			assertEquals(18, computerDAO.count());
			List<Computer> list = computerDAO.listByNameWithPagination("test", 3, 3);
			assertEquals(3, list.size());
			assertEquals("test4", list.get(0).getName());
			assertEquals("test5", list.get(1).getName());
			assertEquals("test6", list.get(2).getName());
		}
	}
	
	@Test
	public void testInsertComputer() throws SQLException, DAOException {
		Computer computer = new ComputerBuilder().setName("test").build();
		assertEquals(0, computerDAO.count());
		computerDAO.insert(computer);
		List<Computer> list = computerDAO.list();
		assertEquals(1, list.size());
		assertEquals(computer.getName(), list.get(0).getName());
	}
	
	@Test
	public void testUpdateComputer() throws SQLException, DAOException {
		try(Connection connect = dataSource.getConnection();
				PreparedStatement statement = connect.prepareStatement("INSERT INTO computer (id,name) VALUES (1,'test')")) {
			Computer computer = new ComputerBuilder().setId(1L).setName("test2").build();
			statement.execute();
			computerDAO.update(computer);
			assertEquals(computer, computerDAO.find(1L));
		}
	}
	
	@Test
	public void testDeleteComputer() throws SQLException, DAOException {
		try(Connection connect = dataSource.getConnection();
				PreparedStatement statement = connect.prepareStatement("INSERT INTO computer (id,name) VALUES (1,'test')")) {
			statement.execute();
			assertEquals(1, computerDAO.count());
			computerDAO.delete(1L);
			assertEquals(0, computerDAO.count());
		}
	}

}

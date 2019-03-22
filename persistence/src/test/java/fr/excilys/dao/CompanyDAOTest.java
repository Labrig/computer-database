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

import fr.excilys.config.SpringDAOTestConfiguration;
import fr.excilys.exception.DAOException;
import fr.excilys.model.Company;

public class CompanyDAOTest {

	private static HikariDataSource dataSource;
	
	private static CompanyDAO companyDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws NoSuchFieldException, SecurityException {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringDAOTestConfiguration.class);
		dataSource = vApplicationContext.getBean("dataSource", HikariDataSource.class);
		companyDAO = vApplicationContext.getBean("companyDAO", CompanyDAO.class);
	}
	
	@Before
	public void setUp() throws SQLException {
		try(Connection connect = dataSource.getConnection();
				PreparedStatement statement = connect.prepareStatement("DELETE FROM company")) {
			statement.execute();
		}
	}
	
	@Test
	public void testFindAndListCompany() throws SQLException, DAOException {
		try(Connection connect = dataSource.getConnection();
				PreparedStatement statement = connect.prepareStatement("INSERT INTO company (name) VALUES ('test')")) {
			assertEquals(0, companyDAO.findAll().size());
			statement.execute();
			List<Company> list = companyDAO.findAll();
			assertEquals(1, list.size());
			Company company = companyDAO.getById(list.get(0).getId());
			assertEquals(list.get(0), company);
		}
	}
}

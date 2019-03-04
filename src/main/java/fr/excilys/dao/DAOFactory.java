package fr.excilys.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

/**
 * The factory of the DAO
 * Regraoup all the DAOs and the connection to the database
 * 
 * @author Matheo
 */
public class DAOFactory {
	
	private static HikariDataSource dataSource = new HikariDataSource();
	
	private static DAOFactory instance = new DAOFactory();
	
	private Logger logger = LoggerFactory.getLogger(DAOFactory.class);

	private DAOFactory(){
		Properties connectionProps = new Properties();
		try {
			connectionProps.load(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"configuration.properties"));
			dataSource.setJdbcUrl(connectionProps.getProperty("URL"));
			dataSource.setUsername(connectionProps.getProperty("USERNAME"));
			dataSource.setPassword(connectionProps.getProperty("PASSWORD"));
			dataSource.setDriverClassName(connectionProps.getProperty("DRIVER"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @return the DAOFactory
	 */
	public static DAOFactory getInstance() {
		return instance;
	}
	
	/**
	 * @return the connection to the database
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	/**
	 * @return the CompanyDAO
	 */
	public CompanyDAO getCompanyDAO() {
		return CompanyDAO.getInstance();
	}
	
	/**
	 * @return the ComputerDAO
	 */
	public ComputerDAO getComputerDAO() {
		return ComputerDAO.getInstance();
	}
}

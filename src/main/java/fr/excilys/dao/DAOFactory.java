package fr.excilys.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The factory of the DAO
 * Regraoup all the DAOs and the connection to the database
 * 
 * @author Matheo
 */
public class DAOFactory {
	
	private static final String URL="jdbc:mysql://localhost/computer-database-db";
	private static final String USERNAME="admincdb";
	private static final String PASSWORD="qwerty1234";
	private static final String DRIVER="com.mysql.cj.jdbc.Driver";
	
	private static DAOFactory instance = new DAOFactory();
	
	private Logger logger = LoggerFactory.getLogger(DAOFactory.class);

	private DAOFactory(){
		try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
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
		return DriverManager.getConnection(URL,USERNAME,PASSWORD);
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

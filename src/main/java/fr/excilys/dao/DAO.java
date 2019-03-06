package fr.excilys.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.ValidationException;

/**
 * Interface of the DAO of an object
 * 
 * @author Matheo
 *
 * @param <T> the object to persist
 */
public abstract class DAO<T> {
	
	private static HikariDataSource dataSource = new HikariDataSource();
	
	private Logger logger = LoggerFactory.getLogger(DAO.class);

	public DAO(){
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
	 * @return the connection to the database
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	/**
	 * Insert an object in the database
	 * 
	 * @param object the object to insert
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public abstract void insert(T object) throws DAOException, ValidationException;
	
	/**
	 * Update an object in the database
	 * 
	 * @param object the object to update
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public abstract void update(T object) throws DAOException, ValidationException;
	
	/**
	 * Delete an object in the database with the specified id
	 * 
	 * @param id the object id to delete
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public abstract void delete(Long id) throws DAOException, ValidationException;
	
	/**
	 * Find an object in database with the id
	 * 
	 * @param id the object id to find
	 * @return the object find or empty object if not found
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public abstract T find(Long id) throws DAOException, ValidationException;
	
	/**
	 * List all object in database
	 * 
	 * @return the list of objects
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public abstract List<T> list() throws DAOException;
	
	/**
	 * Count the number of object in the database
	 * 
	 * @return the number of object in database
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public abstract int count() throws DAOException;
	
	/**
	 * Convert the result of the query in business object
	 * 
	 * @param result the result of the query
	 * @return the object build with the result
	 * @throws SQLException thrown if a problem occur during the communication
	 * @throws DAOException 
	 */
	public abstract T mapResultSet(ResultSet result) throws DAOException;
	
}

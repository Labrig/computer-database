package fr.excilys.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zaxxer.hikari.HikariDataSource;

import fr.excilys.exceptions.DAOException;

/**
 * Interface of the DAO of an object
 * 
 * @author Matheo
 *
 * @param <T> the object to persist
 */
public abstract class DAO<T> {
	
	@Autowired
	private HikariDataSource dataSource;
	
	/**
	 * @return the connection to the database
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	/**
	 * Insert an object in the database
	 * 
	 * @param object the object to insert
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public abstract void insert(T object) throws DAOException;
	
	/**
	 * Update an object in the database
	 * 
	 * @param object the object to update
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public abstract void update(T object) throws DAOException;
	
	/**
	 * Delete an object in the database with the specified id
	 * 
	 * @param id the object id to delete
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public abstract void delete(Long id) throws DAOException;
	
	/**
	 * Find an object in database with the id
	 * 
	 * @param id the object id to find
	 * @return the object find or empty object if not found
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public abstract T find(Long id) throws DAOException;
	
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

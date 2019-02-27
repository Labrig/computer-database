package fr.excilys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface of the DAO of an object
 * 
 * @author Matheo
 *
 * @param <T> the object to persist
 */
public interface DAO<T> {
	
	/**
	 * Insert an object in the database
	 * 
	 * @param object the object to insert
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public void insert(T object) throws SQLException;
	
	/**
	 * Update an object in the database
	 * 
	 * @param object the object to update
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public void update(T object) throws SQLException;
	
	/**
	 * Delete an object in the database with the specified id
	 * 
	 * @param id the object id to delete
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public void delete(Long id) throws SQLException;
	
	/**
	 * Find an object in database with the id
	 * 
	 * @param id the object id to find
	 * @return the object find or empty object if not found
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public T find(Long id) throws SQLException;
	
	/**
	 * List all object in database
	 * 
	 * @return the list of objects
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public List<T> list() throws SQLException;
	
	/**
	 * Count the number of object in the database
	 * 
	 * @return the number of object in database
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public int count() throws SQLException;
	
	/**
	 * Convert the result of the query in business object
	 * 
	 * @param result the result of the query
	 * @return the object build with the result
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public T mapResultSet(ResultSet result) throws SQLException;
	
}

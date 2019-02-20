package fr.excilys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Matheo
 *
 */
public interface DAO<T> {
	
	public void insert(T object) throws SQLException;
	public void update(T object) throws SQLException;
	public void delete(Long id) throws SQLException;
	public T find(Long id) throws SQLException;
	public List<T> list() throws SQLException;
	public T mapResultSet(ResultSet result) throws SQLException;
	
}

package fr.excilys.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.dao.DAOFactory;
import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.ValidationException;
import fr.excilys.model.Computer;

/**
 * Manager of the computer model. Allow :
 * - create
 * - update
 * - delete
 * - find
 * - list
 * @author Matheo
 */
@Service
public class ComputerService {
	
	@Autowired
	private DAOFactory daoFactory;
	
	private ComputerService() { }
	
	/**
	 * @return the list of all computers
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public List<Computer> list() throws DAOException {
		return daoFactory.getComputerDAO().list();
	}
	
	/**
	 * @param computerId the id of the desired computer
	 * @return the desired computer
	 * @throws SQLException 
	 * @throws ValidationException 
	 * @throws DAOException 
	 */
	public Computer find(Long computerId) throws ValidationException, DAOException {
		return daoFactory.getComputerDAO().find(computerId);
	}
	
	/**
	 * create a new computer
	 * @param computer
	 * @throws SQLException 
	 * @throws ValidationException 
	 * @throws DAOException 
	 */
	public void create(Computer computer) throws ValidationException, DAOException {
		daoFactory.getComputerDAO().insert(computer);
	}
	
	/**
	 * update a the computer in param based on his id
	 * @param computer
	 * @return the computer updated
	 * @throws SQLException 
	 * @throws ValidationException 
	 * @throws DAOException 
	 */
	public void update(Computer computer) throws ValidationException, DAOException {
		daoFactory.getComputerDAO().update(computer);
	}
	
	/**
	 * @param computer the computer to delete
	 * @throws SQLException 
	 * @throws ValidationException 
	 * @throws DAOException 
	 */
	public void delete(Long computerId) throws ValidationException, DAOException {
		daoFactory.getComputerDAO().delete(computerId);
	}
	
	/**
	 * List the computer with the name which contain the name parameter
	 * 
	 * @param name the pattern of search
	 * @return the list of computer found with the name parameter
	 * @throws DAOException 
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public List<Computer> listByName(String name) throws DAOException {
		return daoFactory.getComputerDAO().listByName(name);
	}
	
	/**
	 * List the computer in the database by a group
	 * 
	 * @param start the first computer object to be listed
	 * @param size the number of computer to list
	 * @return the list of computer found
	 * @throws DAOException 
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public List<Computer> listWithPagination(int start, int size) throws DAOException {
		return daoFactory.getComputerDAO().listWithPagination(start, size);
	}
	
	/**
	 * List the computer with the name which contain the name parameter
	 * and restrict them by a group
	 * 
	 * @param name the pattern of search
	 * @param start the first computer object to be listed
	 * @param size the number of computer to list
	 * @return the list of computer found
	 * @throws DAOException 
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public List<Computer> listByNameWithPagination(String name, int start, int size) throws DAOException {
		return daoFactory.getComputerDAO().listByNameWithPagination(name, start, size);
	}
	
	/**
	 * Count the number of object in the database
	 * 
	 * @return the number of object in database
	 * @throws DAOException 
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public int count() throws DAOException {
		return daoFactory.getComputerDAO().count();
	}
}

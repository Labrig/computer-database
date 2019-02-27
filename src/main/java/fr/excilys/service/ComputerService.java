package fr.excilys.service;

import java.sql.SQLException;
import java.util.List;

import fr.excilys.dao.ComputerDAO;
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
public class ComputerService {

	private static ComputerService instance = new ComputerService();
	private static ComputerDAO dao = ServiceFactory.getInstance().getDaoFactory().getComputerDAO();
	
	private ComputerService() { }
	
	public static ComputerService getInstance() {
		return instance;
	}
	
	/**
	 * @return the list of all computers
	 * @throws SQLException 
	 */
	public List<Computer> list() throws SQLException {
		return dao.list();
	}
	
	/**
	 * @param computerId the id of the desired computer
	 * @return the desired computer
	 * @throws SQLException 
	 */
	public Computer find(Long computerId) throws SQLException {
		return dao.find(computerId);
	}
	
	/**
	 * create a new computer
	 * @param computer
	 * @throws SQLException 
	 */
	public void create(Computer computer) throws SQLException {
		dao.insert(computer);
	}
	
	/**
	 * update a the computer in param based on his id
	 * @param computer
	 * @return the computer updated
	 * @throws SQLException 
	 */
	public void update(Computer computer) throws SQLException {
		dao.update(computer);
	}
	
	/**
	 * @param computer the computer to delete
	 * @throws SQLException 
	 */
	public void delete(Long computerId) throws SQLException {
		dao.delete(computerId);
	}
	
	/**
	 * List the computer with the name which contain the name parameter
	 * 
	 * @param name the pattern of search
	 * @return the list of computer found with the name parameter
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public List<Computer> listByName(String name) throws SQLException{
		return dao.listByName(name);
	}
	
	/**
	 * List the computer in the database by a group
	 * 
	 * @param start the first computer object to be listed
	 * @param size the number of computer to list
	 * @return the list of computer found
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public List<Computer> listWithPagination(int start, int size) throws SQLException{
		return dao.listWithPagination(start, size);
	}
	
	/**
	 * List the computer with the name which contain the name parameter
	 * and restrict them by a group
	 * 
	 * @param name the pattern of search
	 * @param start the first computer object to be listed
	 * @param size the number of computer to list
	 * @return the list of computer found
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public List<Computer> listByNameWithPagination(String name, int start, int size) throws SQLException{
		return dao.listByNameWithPagination(name, start, size);
	}
	
	/**
	 * Count the number of object in the database
	 * 
	 * @return the number of object in database
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public int count() throws SQLException {
		return dao.count();
	}
}

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
}

package fr.excilys.service;

import java.util.List;

import javax.persistence.EntityManager;

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
	private static EntityManager entityManager = ServiceFactory.getInstance().getEntityManager();
	
	private ComputerService() { }
	
	public static ComputerService getInstance() {
		return instance;
	}
	
	/**
	 * @return the list of all computers
	 */
	public List<Computer> listComputer() {
		return entityManager.createQuery("FROM Computer").getResultList();
	}
	
	/**
	 * @param computerId the id of the desired computer
	 * @return the desired computer
	 */
	public Computer findComputer(long computerId) {
		return entityManager.find(Computer.class, computerId);
	}
	
	/**
	 * create a new computer
	 * @param computer
	 */
	public void createComputer(Computer computer) {
		entityManager.getTransaction().begin();
		entityManager.persist(computer);
		entityManager.getTransaction().commit();
		entityManager.clear();
	}
	
	/**
	 * update a the computer in param based on his id
	 * @param computer
	 * @return the computer updated
	 */
	public Computer updateComputer(Computer computer) {
		entityManager.getTransaction().begin();
		Computer newComputer = entityManager.merge(computer);
		entityManager.getTransaction().commit();
		return newComputer;
	}
	
	/**
	 * @param computer the computer to delete
	 */
	public void deleteComputer(Computer computer) {
		entityManager.getTransaction().begin();
		entityManager.remove(findComputer(computer.getId()));
		entityManager.getTransaction().commit();
	}
}

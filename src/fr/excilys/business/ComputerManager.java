package fr.excilys.business;

import java.util.List;

import fr.excilys.model.Computer;

public class ComputerManager extends ModelManager {

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
		entityManager.persist(computer);
	}
	
	/**
	 * update a the computer in param based on his id
	 * @param computer
	 * @return the computer updated
	 */
	public Computer updateComputer(Computer computer) {
		return entityManager.merge(computer);
	}
	
	/**
	 * @param computer the computer to delete
	 */
	public void deleteComputer(Computer computer) {
		entityManager.remove(computer);
	}
}

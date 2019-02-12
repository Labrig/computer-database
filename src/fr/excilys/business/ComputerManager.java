package fr.excilys.business;

import java.util.List;

import fr.excilys.model.Computer;

public class ComputerManager extends ModelManager {

	/**
	 * @return the list of all computers
	 */
	public List<Computer> listComputer() {
		return this.entityManager.createQuery("FROM Computer").getResultList();
	}
	
	/**
	 * @param computerId the id of the desired computer
	 * @return the desired computer
	 */
	public Computer findComputer(long computerId) {
		return this.entityManager.find(Computer.class, computerId);
	}
	
	/**
	 * create a new computer
	 * @param computer
	 */
	public void createComputer(Computer computer) {
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(computer);
		this.entityManager.getTransaction().commit();
	}
	
	/**
	 * update a the computer in param based on his id
	 * @param computer
	 * @return the computer updated
	 */
	public Computer updateComputer(Computer computer) {
		this.entityManager.getTransaction().begin();
		Computer newComputer = this.entityManager.merge(findComputer(computer.getId()));
		this.entityManager.getTransaction().commit();
		return newComputer;
	}
	
	/**
	 * @param computer the computer to delete
	 */
	public void deleteComputer(Computer computer) {
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(findComputer(computer.getId()));
		this.entityManager.getTransaction().commit();
	}
}

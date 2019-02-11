package fr.excilys.business;

import java.util.List;

import fr.excilys.model.Computer;

public class ComputerManager extends ModelManager {

	public List<Computer> listComputer() {
		return entityManager.createQuery("FROM Computer").getResultList();
	}
	
	public Computer findComputer(long computerId) {
		return entityManager.find(Computer.class, computerId);
	}
	
	public void createComputer(Computer computer) {
		entityManager.persist(computer);
	}
	
	public Computer updateComputer(Computer computer) {
		return entityManager.merge(computer);
	}
	
	public void deleteComputer(Computer computer) {
		entityManager.remove(computer);
	}
}

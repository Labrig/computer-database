package fr.excilys.service;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Factory creating all the needed managers
 * (implementing as a singleton)
 * @author Matheo
 */
public class ServiceFactory {

	private static ServiceFactory instance = new ServiceFactory();
	private EntityManager entityManager;
	
	private ServiceFactory() {
		entityManager = Persistence.createEntityManagerFactory("computer-database").createEntityManager();
	}
	
	/**
	 * @return the instance of the factory
	 */
	public static ServiceFactory getInstance() {
		return instance;
	}
	
	/**
	 * create a new manager for the company beans
	 * @return the new manager
	 */
	public static CompanyService getCompanyService() {
		return CompanyService.getInstance();
	}
	
	/**
	 * create a new manager for the computer beans
	 * @return the new manager
	 */
	public static ComputerService getComputerService() {
		return ComputerService.getInstance();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
}

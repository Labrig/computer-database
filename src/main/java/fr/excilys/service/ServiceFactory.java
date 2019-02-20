package fr.excilys.service;

import fr.excilys.dao.DAOFactory;

/**
 * Factory creating all the needed managers
 * (implementing as a singleton)
 * @author Matheo
 */
public class ServiceFactory {

	private static ServiceFactory instance = new ServiceFactory();
	private static DAOFactory daoFactory = DAOFactory.getInstance();
	
	private ServiceFactory() { }
	
	/**
	 * @return the instance of the factory
	 */
	public static ServiceFactory getInstance() {
		return instance;
	}
	
	public DAOFactory getDaoFactory() {
		return daoFactory;
	}

	/**
	 * create a new manager for the company beans
	 * @return the new manager
	 */
	public CompanyService getCompanyService() {
		return CompanyService.getInstance();
	}
	
	/**
	 * create a new manager for the computer beans
	 * @return the new manager
	 */
	public ComputerService getComputerService() {
		return ComputerService.getInstance();
	}
}

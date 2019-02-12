package fr.excilys.business;

/**
 * Factory creating all the needed managers
 * (implementing as a singleton)
 * @author Matheo
 */
public class ManagerFactory {

	private static ManagerFactory instance = new ManagerFactory();
	
	private ManagerFactory() { }
	
	/**
	 * @return the instance of the factory
	 */
	public static ManagerFactory getInstance() {
		return instance;
	}
	
	/**
	 * create a new manager for the company beans
	 * @return the new manager
	 */
	public CompanyManager getCompanyManager() {
		return new CompanyManager();
	}
	
	/**
	 * create a new manager for the computer beans
	 * @return the new manager
	 */
	public ComputerManager getComputerManager() {
		return new ComputerManager();
	}
}

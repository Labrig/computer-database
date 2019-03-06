package fr.excilys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Factory creating all the needed managers
 * (implementing as a singleton)
 * @author Matheo
 */
@Service
public class ServiceFactory {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;

	private ServiceFactory() { }
	
	/**
	 * create a new manager for the company beans
	 * @return the new manager
	 */
	public CompanyService getCompanyService() {
		return this.companyService;
	}
	
	/**
	 * create a new manager for the computer beans
	 * @return the new manager
	 */
	public ComputerService getComputerService() {
		return this.computerService;
	}
}

package fr.excilys.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DAOFactory {

	@Autowired
	private CompanyDAO companyDAO;
	
	@Autowired
	private ComputerDAO computerDAO;

	private DAOFactory() { }
	
	/**
	 * @return the companyDAO
	 */
	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	/**
	 * @return the computerDAO
	 */
	public ComputerDAO getComputerDAO() {
		return computerDAO;
	}
}

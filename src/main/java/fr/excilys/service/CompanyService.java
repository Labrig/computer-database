package fr.excilys.service;

import java.sql.SQLException;
import java.util.List;

import fr.excilys.dao.CompanyDAO;
import fr.excilys.model.Company;

/**
 * Manager of the computer model. Allow only list.
 * @author Matheo
 */
public class CompanyService {
	
	private static CompanyService instance = new CompanyService();
	private static CompanyDAO dao = ServiceFactory.getInstance().getDaoFactory().getCompanyDAO();
	
	private CompanyService() { }
	
	public static CompanyService getInstance() {
		return instance;
	}

	/**
	 * @return a list of all companies
	 * @throws SQLException 
	 */
	public List<Company> list() throws SQLException {
		return dao.list();
	}
	
	/**
	 * @param companyId the id of the desired company
	 * @return the desired company
	 * @throws SQLException 
	 */
	public Company find(Long companyId) throws SQLException {
		return dao.find(companyId);
	}
}

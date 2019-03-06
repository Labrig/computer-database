package fr.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.dao.DAOFactory;
import fr.excilys.exceptions.DAOException;
import fr.excilys.model.Company;

/**
 * Manager of the computer model. Allow only list.
 * @author Matheo
 */
@Service
public class CompanyService {

	@Autowired
	private DAOFactory daoFactory;
	
	private CompanyService() { }
	
	/**
	 * @return a list of all companies
	 * @throws DAOException
	 */
	public List<Company> list() throws DAOException {
		return daoFactory.getCompanyDAO().list();
	}
	
	/**
	 * @param companyId the id of the desired company
	 * @return the desired company
	 * @throws DAOException
	 */
	public Company find(Long companyId) throws DAOException {
		return daoFactory.getCompanyDAO().find(companyId);
	}
	
	/**
	 * @param companyId
	 * @throws DAOException
	 */
	public void delete(Long companyId) throws DAOException {
		daoFactory.getCompanyDAO().delete(companyId);
	}
}

package fr.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.dao.CompanyDAO;
import fr.excilys.exceptions.DAOException;
import fr.excilys.model.Company;

/**
 * Manager of the computer model. Allow only list.
 * @author Matheo
 */
@Service
public class CompanyService {

	@Autowired
	private CompanyDAO dao;
	
	private CompanyService() { }
	
	/**
	 * @return a list of all companies
	 * @throws DAOException
	 */
	public List<Company> list() throws DAOException {
		return dao.list();
	}
	
	/**
	 * @param companyId the id of the desired company
	 * @return the desired company
	 * @throws DAOException
	 */
	public Company find(Long companyId) throws DAOException {
		return dao.find(companyId);
	}
	
	/**
	 * @param companyId
	 * @throws DAOException
	 */
	public void delete(Long companyId) throws DAOException {
		dao.delete(companyId);
	}
}

package fr.excilys.service;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.dao.CompanyDAO;
import fr.excilys.dao.ComputerDAO;
import fr.excilys.exceptions.DAOException;
import fr.excilys.model.Company;

/**
 * Manager of the computer model. Allow only list.
 * @author Matheo
 */
@Service
public class CompanyService {

	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;
	
	private CompanyService(CompanyDAO companyDAO, ComputerDAO computerDAO) {
		this.companyDAO = companyDAO;
		this.computerDAO = computerDAO;
	}
	
	/**
	 * @return a list of all companies
	 * @throws DAOException
	 */
	public List<Company> list() {
		return companyDAO.findAll();
	}
	
	/**
	 * @param companyId the id of the desired company
	 * @return the desired company
	 * @throws DAOException
	 */
	public Company find(Long companyId) throws DAOException {
		try {
			return companyDAO.getById(companyId);
		} catch (EmptyResultDataAccessException e) {
			throw new DAOException("company not found with the id "+companyId);
		}
	}
	
	/**
	 * @param companyId
	 * @throws DAOException
	 */
	@Transactional
	public void delete(Long companyId) throws DAOException {
		computerDAO.deleteByIdCompany(companyId);
		if(companyDAO.deleteById(companyId) == 0) {
			throw new DAOException("can not delete the company with the id "+companyId);
		}
	}
}

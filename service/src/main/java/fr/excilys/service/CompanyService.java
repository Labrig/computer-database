package fr.excilys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.dao.CompanyDAO;
import fr.excilys.dao.ComputerDAO;
import fr.excilys.dto.CompanyDTO;
import fr.excilys.exception.ServiceException;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.model.Company;

/**
 * Manager of the computer model. Allow only list.
 * @author Matheo
 */
@Service
public class CompanyService {

	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;
	private CompanyMapper mapper;
	
	private CompanyService(CompanyDAO companyDAO, ComputerDAO computerDAO, CompanyMapper mapper) {
		this.companyDAO = companyDAO;
		this.computerDAO = computerDAO;
		this.mapper = mapper;
	}
	
	/**
	 * @return a list of all companies
	 * @throws ServiceException
	 */
	public List<CompanyDTO> list() {
		List<CompanyDTO> list = new ArrayList<>();
		for(Company company : companyDAO.findAll())
			list.add(mapper.mapObjectInDTO(company));
		return list;
	}
	
	/**
	 * @param companyId the id of the desired company
	 * @return the desired company
	 * @throws ServiceException
	 */
	public CompanyDTO find(Long companyId) throws ServiceException {
		try {
			return mapper.mapObjectInDTO(companyDAO.getById(companyId));
		} catch (EmptyResultDataAccessException e) {
			throw new ServiceException("company not found with the id "+companyId);
		}
	}
	
	/**
	 * @param companyId
	 * @throws ServiceException
	 */
	@Transactional
	public void delete(Long companyId) throws ServiceException {
		computerDAO.deleteByIdCompany(companyId);
		if(companyDAO.deleteById(companyId) == 0) {
			throw new ServiceException("can not delete the company with the id "+companyId);
		}
	}
}

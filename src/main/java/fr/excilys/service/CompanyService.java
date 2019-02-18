package fr.excilys.service;

import java.util.List;

import javax.persistence.EntityManager;

import fr.excilys.model.Company;

/**
 * Manager of the computer model. Allow only list.
 * @author Matheo
 */
public class CompanyService {
	
	private static CompanyService instance = new CompanyService();
	private static EntityManager entityManager = ServiceFactory.getInstance().getEntityManager();
	
	private CompanyService() { }
	
	public static CompanyService getInstance() {
		return instance;
	}

	/**
	 * @return a list of all companies
	 */
	public List<Company> listCompany() {
		return entityManager.createQuery("FROM Company").getResultList();
	}
	
	/**
	 * @param companyId the id of the desired company
	 * @return the desired company
	 */
	public Company findCompany(long companyId) {
		return entityManager.find(Company.class, companyId);
	}
}

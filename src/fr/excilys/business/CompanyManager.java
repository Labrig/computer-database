package fr.excilys.business;

import java.util.List;

import fr.excilys.model.Company;

/**
 * Manager of the computer model. Allow only list.
 * @author Matheo
 */
public class CompanyManager extends ModelManager {
	
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

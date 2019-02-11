package fr.excilys.business;

import java.util.List;

import fr.excilys.model.Company;

public class CompanyManager extends ModelManager {
	
	public List<Company> listCompany() {
		return entityManager.createQuery("FROM Company").getResultList();
	}
}

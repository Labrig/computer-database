package fr.excilys.business;

import java.util.List;

import org.junit.Test;

import fr.excilys.model.Company;

public class CompanyManagerTest {

	@Test
	public void test() {
		CompanyManager manager = ManagerFactory.getInstance().getCompanyManager();
		List<Company> listCompany = manager.listCompany();
		for(Company company : listCompany) {
			System.out.println(company.getName());
		}
	}

}

package fr.excilys.business;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import fr.excilys.model.Company;

public class CompanyManagerTest {

	@Test
	public void test() {
		CompanyManager manager = new CompanyManager();
		List<Company> listCompany = manager.listCompany();
		for(Company company : listCompany) {
			System.out.println(company.getName());
		}
	}

}

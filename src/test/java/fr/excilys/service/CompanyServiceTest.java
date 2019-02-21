package fr.excilys.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.excilys.model.Company;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ServiceFactory;

public class CompanyServiceTest {

//	private static CompanyService manager = ServiceFactory.getInstance().getCompanyService();
//	private static Field entityManagerField;
//	
//	@BeforeClass
//	public static void setUpBeforeClass() {
//		try {
//			entityManagerField = CompanyService.class.getDeclaredField("entityManager");
//			entityManagerField.setAccessible(true);
//			entityManagerField.set(manager, Persistence.createEntityManagerFactory("computer-database-test").createEntityManager());
//		} catch (NoSuchFieldException | SecurityException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testListCompanyEmpty() {
//		List<Company> listCompany = manager.listCompany();
//		assertEquals(0, listCompany.size());
//	}
//	
//	@Test
//	public void testFindCompanyEmpty() {
//		Company company = manager.findCompany(1);
//		assertNull(company);
//	}
//	
//	@Test
//	public void testListAndFindCompanyWith1() {
//		insertNewTestCompany();
//		List<Company> listCompany = manager.listCompany();
//		assertEquals(1, listCompany.size());
//		Company company = manager.findCompany(listCompany.get(0).getId());
//		assertEquals(listCompany.get(0), company);
//		deleteAllCompany();
//	}
//
//	private void deleteAllCompany() {
//		try {
//			EntityManager entityManager = (EntityManager)entityManagerField.get(manager);
//			for(Object company : entityManager.createQuery("FROM Company").getResultList()) {
//				entityManager.getTransaction().begin();
//				entityManager.remove(company);
//				entityManager.getTransaction().commit();
//			}
//		} catch (IllegalArgumentException | IllegalAccessException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void insertNewTestCompany() {
//		try {
//			Company company = new Company();
//			company.setName("test");
//			EntityManager entityManager = (EntityManager)entityManagerField.get(manager);
//			entityManager.getTransaction().begin();
//			entityManager.persist(company);
//			entityManager.getTransaction().commit();
//		} catch (IllegalArgumentException | IllegalAccessException e) {
//			e.printStackTrace();
//		}
//	}

}

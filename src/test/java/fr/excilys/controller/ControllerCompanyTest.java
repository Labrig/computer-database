package fr.excilys.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.exceptions.DTOException;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.model.Company;
import fr.excilys.service.CompanyService;
import fr.excilys.view.CliView;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CompanyService.class})
public class ControllerCompanyTest {

	@Test
	public void testListCompany() throws SQLException {
		List<Company> listCompany = new ArrayList<>();
		for(int i = 1; i < 4 ; i++) {
			Company company = new Company();
			company.setId(new Long(i));
			company.setName("test"+i);
			listCompany.add(company);
		}
		PowerMockito.mockStatic(CompanyService.class);
		CompanyService service = mock(CompanyService.class);
		when(CompanyService.getInstance()).thenReturn(service);
		when(service.list()).thenReturn(listCompany);
		Controller controller = new Controller(new CliView());
		try {
			controller.executeCommand("/l company");
		} catch (NotCommandeException e) {
			e.printStackTrace();
		} catch (ComputerFormatException e) {
			e.printStackTrace();
		} catch (DTOException e) {
			e.printStackTrace();
		}
	}

}

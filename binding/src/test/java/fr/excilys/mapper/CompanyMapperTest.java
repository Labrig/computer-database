package fr.excilys.mapper;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.config.SpringBindingTestConfiguration;
import fr.excilys.dto.CompanyDTO;
import fr.excilys.exception.CompanyIdFormatException;
import fr.excilys.exception.CompanyMappingException;
import fr.excilys.exception.MappingException;
import fr.excilys.model.Company;

public class CompanyMapperTest {

private static CompanyMapper mapper;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringBindingTestConfiguration.class);
		mapper = vApplicationContext.getBean("companyMapper", CompanyMapper.class);
	}
	
	@Test
	public void testMapObjectInDTO() {
		CompanyDTO dto = mapper.mapObjectInDTO(new Company());
		assertNull(dto.getId());
		assertNull(dto.getName());
	}
	
	@Test
	public void testMapDTOInObject() throws MappingException {
		Company company = mapper.mapDTOInObject(new CompanyDTO());
		assertNull(company.getId());
		assertNull(company.getName());
	}
	
	@Test(expected = CompanyIdFormatException.class)
	public void expectExceptionWhenIdNotNumber() throws CompanyMappingException {
		CompanyDTO dto = new CompanyDTO();
		dto.setId("test");
		mapper.mapDTOInObject(dto);
	}

}

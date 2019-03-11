package fr.excilys.mapper;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.config.SpringConfiguration;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.mapping.ComputerDateFormatException;
import fr.excilys.exceptions.mapping.ComputerIdCompanyFormatException;
import fr.excilys.exceptions.mapping.ComputerIdFormatException;
import fr.excilys.exceptions.mapping.ComputerMappingException;
import fr.excilys.model.Computer;

public class ComputerMapperTest {

	private static ComputerMapper mapper;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		mapper = vApplicationContext.getBean("computerMapper", ComputerMapper.class);
	}
	
	@Test
	public void testMapObjectInDTO() {
		ComputerDTO dto = mapper.mapObjectInDTO(new Computer());
		assertNull(dto.getId());
		assertNull(dto.getName());
		assertNull(dto.getIntroduced());
		assertNull(dto.getDiscontinued());
		assertNull(dto.getCompanyId());
		assertNull(dto.getCompanyName());
	}
	
	@Test
	public void testMapDTOInObject() throws ComputerMappingException {
		Computer computer = mapper.mapDTOInObject(new ComputerDTO());
		assertNull(computer.getId());
		assertNull(computer.getName());
		assertNull(computer.getIntroduced());
		assertNull(computer.getDiscontinued());
		assertNull(computer.getCompany().getId());
		assertNull(computer.getCompany().getName());
	}
	
	@Test(expected = ComputerIdFormatException.class)
	public void expectExceptionWhenIdNotNumber() throws ComputerMappingException {
		ComputerDTO dto = new ComputerDTO();
		dto.setId("test");
		mapper.mapDTOInObject(dto);
	}
	
	@Test(expected = ComputerDateFormatException.class)
	public void expectExceptionWhenIntroNotDate() throws ComputerMappingException {
		ComputerDTO dto = new ComputerDTO();
		dto.setIntroduced("test");
		mapper.mapDTOInObject(dto);
	}
	
	@Test(expected = ComputerDateFormatException.class)
	public void expectExceptionWhenDiscoNotDate() throws ComputerMappingException {
		ComputerDTO dto = new ComputerDTO();
		dto.setDiscontinued("test");
		mapper.mapDTOInObject(dto);
	}
	
	@Test(expected = ComputerIdCompanyFormatException.class)
	public void expectExceptionWhenIdCompanyNotNumber() throws ComputerMappingException {
		ComputerDTO dto = new ComputerDTO();
		dto.setCompanyId("test");
		mapper.mapDTOInObject(dto);
	}

}

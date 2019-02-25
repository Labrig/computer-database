package fr.excilys.mapper;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.DTOException;
import fr.excilys.model.Computer;

public class ComputerMapperTest {

	@Test
	public void testMapObjectInDTO() {
		ComputerDTO dto = ComputerMapper.getInstance().mapObjectInDTO(new Computer());
		assertNull(dto.getId());
		assertNull(dto.getName());
		assertNull(dto.getIntroduced());
		assertNull(dto.getDiscontinued());
		assertNull(dto.getCompanyId());
		assertNull(dto.getCompanyName());
	}
	
	@Test
	public void testMapDTOInObject() throws DTOException {
		Computer computer = ComputerMapper.getInstance().mapDTOInObject(new ComputerDTO());
		assertNull(computer.getId());
		assertNull(computer.getName());
		assertNull(computer.getIntroduced());
		assertNull(computer.getDiscontinued());
		assertNull(computer.getCompany().getId());
		assertNull(computer.getCompany().getName());
	}

}

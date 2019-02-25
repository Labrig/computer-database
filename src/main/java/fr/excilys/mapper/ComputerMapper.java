package fr.excilys.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.DTOException;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

public class ComputerMapper implements ObjectMapper<Computer, ComputerDTO> {

	private static final ComputerMapper instance = new ComputerMapper();
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private ComputerMapper() { }

	public static ComputerMapper getInstance() {
		return instance;
	}

	@Override
	public Computer mapDTOInObject(ComputerDTO dto) throws DTOException {
		Computer computer = new Computer();
		try {
			computer.setId(this.convertStringToId(dto.getId()));
		} catch(NumberFormatException e) {
			throw new DTOException("the computer id is not a long");
		}
		computer.setName(dto.getName());
		try {
			computer.setIntroduced(this.convertStringToDate(dto.getIntroduced()));
		} catch(ParseException e) {
			throw new DTOException("the introducted date is not good format");
		}
		try {
			computer.setDiscontinued(this.convertStringToDate(dto.getDiscontinued()));
		} catch(ParseException e) {
			throw new DTOException("the discontinued date is not good format");
		}
		Company company = new Company();
		try {
			company.setId(this.convertStringToId(dto.getCompanyId()));
		} catch(NumberFormatException e) {
			throw new DTOException("the company id is not a long");
		}
		company.setName(dto.getCompanyName());
		computer.setCompany(company);
		return computer;
	}

	@Override
	public ComputerDTO mapObjectInDTO(Computer computer) {
		ComputerDTO dto = new ComputerDTO();
		dto.setId(this.convertIdToString(computer.getId()));
		dto.setName(computer.getName());
		dto.setIntroduced(this.convertDateToString(computer.getIntroduced()));
		dto.setDiscontinued(this.convertDateToString(computer.getDiscontinued()));
		if(computer.getCompany() != null) {
			dto.setCompanyId(this.convertIdToString(computer.getCompany().getId()));
			dto.setCompanyName(computer.getCompany().getName());
		}
		return dto;
	}
	
	public String convertDateToString(Date date) {
		return date == null ? null : formatter.format(date);
	}
	
	public Date convertStringToDate(String date) throws ParseException {
		return date == null || "".equals(date) ? null : formatter.parse(date);
	}
	
	public String convertIdToString(Long id) {
		return id == null ? null : String.valueOf(id);
	}
	
	public Long convertStringToId(String id) {
		return id == null || "0".equals(id) ? null : Long.valueOf(id);
	}
}

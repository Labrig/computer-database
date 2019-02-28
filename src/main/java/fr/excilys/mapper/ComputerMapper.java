package fr.excilys.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.MappingException;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

public class ComputerMapper implements ObjectMapper<Computer, ComputerDTO> {

	private static final ComputerMapper instance = new ComputerMapper();
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private Logger logger = LoggerFactory.getLogger(ComputerMapper.class);
	
	private ComputerMapper() { }

	/**
	 * @return the singleton of ComputerMapper
	 */
	public static ComputerMapper getInstance() {
		return instance;
	}

	@Override
	public Computer mapDTOInObject(ComputerDTO dto) throws MappingException {
		Computer computer = new Computer();
		try {
			computer.setId(this.convertStringToId(dto.getId()));
		} catch(NumberFormatException e) {
			logger.error(e.getMessage(), e);
			throw new MappingException("the computer id is not a long");
		}
		computer.setName(dto.getName());
		try {
			computer.setIntroduced(this.convertStringToDate(dto.getIntroduced()));
		} catch(ParseException e) {
			logger.error(e.getMessage(), e);
			throw new MappingException("the introducted date is not good format");
		}
		try {
			computer.setDiscontinued(this.convertStringToDate(dto.getDiscontinued()));
		} catch(ParseException e) {
			logger.error(e.getMessage(), e);
			throw new MappingException("the discontinued date is not good format");
		}
		Company company = new Company();
		try {
			company.setId(this.convertStringToId(dto.getCompanyId()));
		} catch(NumberFormatException e) {
			logger.error(e.getMessage(), e);
			throw new MappingException("the company id is not a long");
		}
		company.setName(dto.getCompanyName());
		computer.setCompany(company);
		logger.info("the dto has been converted into computer");
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
		logger.info("the computer has been converted into dto");
		return dto;
	}
	
	/**
	 * Convert a type Date into String with the formatter yyyy-MM-dd
	 * 
	 * @param date with type Date
	 * @return date with type String
	 */
	public String convertDateToString(Date date) {
		return date == null ? null : formatter.format(date);
	}
	
	/**
	 * Convert a type String into Date with the formatter yyyy-MM-dd
	 * 
	 * @param date with type String
	 * @return date with type Date
	 */
	public Date convertStringToDate(String date) throws ParseException {
		return date == null || "".equals(date) ? null : formatter.parse(date);
	}
	
	/**
	 * Convert the Long id in a string if not null
	 * else return null
	 * 
	 * @param Long id
	 * @return String id
	 */
	public String convertIdToString(Long id) {
		return id == null || id == 0 ? null : String.valueOf(id);
	}
	
	/**
	 * Parse the String id in a Long if not null and different of "0"
	 * else return null
	 * 
	 * @param String id
	 * @return Long id
	 */
	public Long convertStringToId(String id) {
		return id == null || "0".equals(id) ? null : Long.valueOf(id);
	}
}

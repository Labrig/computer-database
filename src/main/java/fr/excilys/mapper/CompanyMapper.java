package fr.excilys.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.exceptions.mapping.CompanyIdFormatException;
import fr.excilys.exceptions.mapping.CompanyMappingException;
import fr.excilys.model.Company;

/**
 * The mapper of the company objcet
 * Implement the ObjectMapper interface
 * 
 * @author Matheo
 */
@Component
public class CompanyMapper implements ObjectMapper<Company, CompanyDTO> {
	
	private Logger logger = LoggerFactory.getLogger(CompanyMapper.class);
	
	private CompanyMapper() { }
	
	@Override
	public Company mapDTOInObject(CompanyDTO dto) throws CompanyMappingException {
		Company company = new Company();
		try {
			company.setId(Long.valueOf(dto.getId()));
		} catch(NumberFormatException e) {
			logger.error(e.getMessage(), e);
			throw new CompanyIdFormatException();
		}
		company.setName(dto.getName());
		logger.info("the dto has been converted into company");
		return company;
	}
	
	@Override
	public CompanyDTO mapObjectInDTO(Company company) {
		CompanyDTO dto = new CompanyDTO();
		dto.setId(String.valueOf(company.getId()));
		dto.setName(company.getName());
		logger.info("the company has been converted into dto");
		return dto;
	}
}

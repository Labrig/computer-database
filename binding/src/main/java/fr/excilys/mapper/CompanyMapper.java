package fr.excilys.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.exception.CompanyIdFormatException;
import fr.excilys.exception.CompanyMappingException;
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
			company.setId(this.convertStringToId(dto.getId()));
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
		dto.setId(this.convertIdToString(company.getId()));
		dto.setName(company.getName());
		logger.info("the company has been converted into dto");
		return dto;
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

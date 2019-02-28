package fr.excilys.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.exceptions.MappingException;
import fr.excilys.model.Company;

/**
 * The mapper of the company objcet
 * Implement the ObjectMapper interface
 * 
 * @author Matheo
 */
public class CompanyMapper implements ObjectMapper<Company, CompanyDTO> {

	private static final CompanyMapper instance = new CompanyMapper();
	
	private Logger logger = LoggerFactory.getLogger(CompanyMapper.class);
	
	private CompanyMapper() { }

	/**
	 * @return the singleton of CompanyMapper
	 */
	public static CompanyMapper getInstance() {
		return instance;
	}
	
	@Override
	public Company mapDTOInObject(CompanyDTO dto) throws MappingException {
		Company company = new Company();
		try {
			company.setId(Long.valueOf(dto.getId()));
		} catch(NumberFormatException e) {
			logger.error(e.getMessage(), e);
			throw new MappingException("the company id is not a long");
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

package fr.excilys.mapper;

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
			throw new MappingException("the company id is not a long");
		}
		company.setName(dto.getName());
		return company;
	}
	
	@Override
	public CompanyDTO mapObjectInDTO(Company company) {
		CompanyDTO dto = new CompanyDTO();
		dto.setId(String.valueOf(company.getId()));
		dto.setName(company.getName());
		return dto;
	}
}

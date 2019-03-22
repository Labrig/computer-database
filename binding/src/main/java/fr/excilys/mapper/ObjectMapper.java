package fr.excilys.mapper;

import fr.excilys.exception.MappingException;

/**
 * Interface use to create a mapper between
 * a business object and his DTO
 * @author Matheo
 *
 * @param <O> the business object
 * @param <D> the data transfer object
 */
public interface ObjectMapper<O, D> {

	/**
	 * Transform the DTO object into business object
	 * 
	 * @param dto the DTO object
	 * @return the business object
	 * @throws MappingException 
	 */
	public O mapDTOInObject(D dto) throws MappingException;
	
	/**
	 * Transform the business object into DTO object
	 * 
	 * @param object the business object
	 * @return the DTO object
	 */
	public D mapObjectInDTO(O object);
}

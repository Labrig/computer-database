package fr.excilys.mapper;

import fr.excilys.exceptions.DTOException;

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
	 * @throws DTOException 
	 */
	public O mapDTOInObject(D dto) throws DTOException;
	
	/**
	 * Transform the business object into DTO object
	 * 
	 * @param object the business object
	 * @return the DTO object
	 */
	public D mapObjectInDTO(O object);
}

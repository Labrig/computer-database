package fr.excilys.mapper;

import fr.excilys.exceptions.DTOException;

public interface ObjectMapper<O, D> {

	public O mapDTOInObject(D dto) throws DTOException;
	public D mapObjectInDTO(O object);
}

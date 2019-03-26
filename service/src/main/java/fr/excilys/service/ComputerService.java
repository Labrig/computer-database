package fr.excilys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.excilys.dao.ComputerDAO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.ServiceException;
import fr.excilys.exception.ComputerMappingException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.exception.ComputerValidationException;
import fr.excilys.model.Computer;
import fr.excilys.validator.ComputerValidator;

/**
 * Manager of the computer model. Allow :
 * - create
 * - update
 * - delete
 * - find
 * - list
 * @author Matheo
 */
@Service
public class ComputerService {

	private ComputerDAO dao;
	private ComputerValidator validator;
	private ComputerMapper mapper;
	
	private ComputerService(ComputerDAO dao, ComputerValidator validator, ComputerMapper mapper) {
		this.dao = dao;
		this.validator = validator;
		this.mapper = mapper;
	}
	
	/**
	 * @return the list of all computers
	 * @throws ServiceException 
	 * @throws SQLException 
	 */
	public List<ComputerDTO> list() {
		List<ComputerDTO> list = new ArrayList<>();
		for(Computer computer : dao.findAll())
			list.add(mapper.mapObjectInDTO(computer));
		return list;
	}
	
	/**
	 * @param computerId the id of the desired computer
	 * @return the desired computer
	 * @throws SQLException 
	 * @throws ComputerValidationException 
	 * @throws ServiceException 
	 */
	public ComputerDTO find(Long computerId) throws ComputerValidationException, ServiceException {
		validator.verifyIdNotNull(computerId);
		try {
			return mapper.mapObjectInDTO(dao.getById(computerId));
		} catch (EmptyResultDataAccessException e) {
			throw new ServiceException("computer not found with the id "+computerId);
		}
	}
	
	/**
	 * create a new computer
	 * @param computer
	 * @throws SQLException 
	 * @throws ComputerValidationException 
	 * @throws ServiceException 
	 * @throws ComputerMappingException 
	 */
	public void create(ComputerDTO computerDTO) throws ComputerValidationException, ServiceException, ComputerMappingException {
		Computer computer = mapper.mapDTOInObject(computerDTO);
		validator.verifyComputerNotNull(computer);
		validator.verifyName(computer.getName());
		validator.verifyIntroBeforeDisco(computer);
		if(dao.insert(computer) == 0) {
			throw new ServiceException("can not create the computer "+computer);
		}
	}
	
	/**
	 * update a the computer in param based on his id
	 * @param computer
	 * @return the computer updated
	 * @throws SQLException 
	 * @throws ComputerValidationException 
	 * @throws ServiceException 
	 * @throws ComputerMappingException 
	 */
	public void update(ComputerDTO computerDTO) throws ComputerValidationException, ServiceException, ComputerMappingException {
		Computer computer = mapper.mapDTOInObject(computerDTO);
		validator.verifyComputerNotNull(computer);
		validator.verifyIdNotNull(computer.getId());
		validator.verifyName(computer.getName());
		validator.verifyIntroBeforeDisco(computer);
		if(dao.update(computer) == 0) {
			throw new ServiceException("can not update the computer "+computer);
		}
	}
	
	/**
	 * @param computer the computer to delete
	 * @throws SQLException 
	 * @throws ComputerValidationException 
	 * @throws ServiceException 
	 */
	public void delete(Long computerId) throws ComputerValidationException, ServiceException {
		validator.verifyIdNotNull(computerId);
		if(dao.deleteById(computerId) == 0) {
			throw new ServiceException("can not delete the computer with the id "+computerId);
		}
	}
	
	/**
	 * List the computer with the name which contain the name parameter
	 * 
	 * @param name the pattern of search
	 * @return the list of computer found with the name parameter
	 * @throws ServiceException 
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public List<ComputerDTO> listByName(String name) {
		List<ComputerDTO> list = new ArrayList<>();
		for(Computer computer : dao.findByNameContaining(name))
			list.add(mapper.mapObjectInDTO(computer));
		return list;
	}
	
	/**
	 * List the computer in the database by a group
	 * 
	 * @param start the first computer object to be listed
	 * @param size the number of computer to list
	 * @return the list of computer found
	 * @throws ServiceException 
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public List<ComputerDTO> listWithPagination(int start, int size) {
		List<ComputerDTO> list = new ArrayList<>();
		for(Computer computer : dao.findAllWithPagination(start, size))
			list.add(mapper.mapObjectInDTO(computer));
		return list;
	}
	
	public List<ComputerDTO> listWithPagination(Pageable pageable) {
		List<ComputerDTO> list = new ArrayList<>();
		for(Computer computer : dao.findAll(pageable))
			list.add(mapper.mapObjectInDTO(computer));
		return list;
	}
	
	/**
	 * List the computer with the name which contain the name parameter
	 * and restrict them by a group
	 * 
	 * @param name the pattern of search
	 * @param start the first computer object to be listed
	 * @param size the number of computer to list
	 * @return the list of computer found
	 * @throws ServiceException 
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public List<ComputerDTO> listByNameWithPagination(String name, Pageable pageable) {
		List<ComputerDTO> list = new ArrayList<>();
		for(Computer computer : dao.findByNameContaining(name, pageable))
			list.add(mapper.mapObjectInDTO(computer));
		return list;
	}
	
	/**
	 * Count the number of object in the database
	 * 
	 * @return the number of object in database
	 * @throws ServiceException 
	 * @throws SQLException thrown if a problem occur during the communication
	 */
	public int count() {
		return dao.count();
	}
	
	public int countByName(String name) {
		return dao.countByNameContaining(name);
	}
}

package fr.excilys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.excilys.exceptions.DAOException;
import fr.excilys.model.Computer;
import fr.excilys.model.Computer.ComputerBuilder;

/**
 * The DAO of the computer object
 * 
 * @author Matheo
 */
@Repository
public class ComputerDAO extends DAO<Computer> {
	
	private static final String INSERT_COMPUTER_REQUEST = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (:name, :intro, :disco, :idCompany)";
	private static final String UPDATE_COMPUTER_REQUEST = "UPDATE computer SET name = :name, introduced = :intro, discontinued = :disco, company_id = :idCompany WHERE id = :id";
	private static final String DELETE_COMPUTER_REQUEST = "DELETE FROM computer WHERE id = :id";
	private static final String SELECT_COMPUTER_REQUEST = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = :id";
	private static final String LIST_COMPUTER_REQUEST = "SELECT id, name, introduced, discontinued, company_id FROM computer";
	private static final String LIST_COMPUTER_BY_NAME_REQUEST = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE name LIKE :name";
	private static final String LIMIT_COMPUTER = " LIMIT :start, :size";
	private static final String COUNT_COMPUTER_REQUEST = "SELECT COUNT(id) AS count FROM computer";
	
	@Autowired
	private CompanyDAO companyDAO;
	
	private Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	private ComputerDAO() { }

	@Override
	public void insert(Computer computer) throws DAOException {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("name", computer.getName());
	    parameters.addValue("intro", computer.getIntroduced() == null ? null : new Timestamp(computer.getIntroduced().getTime()), Types.TIMESTAMP);
	    parameters.addValue("disco", computer.getDiscontinued() == null ? null : new Timestamp(computer.getDiscontinued().getTime()), Types.TIMESTAMP);
	    parameters.addValue("idCompany", computer.getCompany() == null ? null : computer.getCompany().getId());

	    NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
	    if(vJdbcTemplate.update(INSERT_COMPUTER_REQUEST, parameters) == 0) {
	    	throw new DAOException("can not insert the computer "+computer.toString());
	    }
	}

	@Override
	public void update(Computer computer) throws DAOException {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", computer.getId());
		parameters.addValue("name", computer.getName());
	    parameters.addValue("intro", computer.getIntroduced() == null ? null : new Timestamp(computer.getIntroduced().getTime()), Types.TIMESTAMP);
	    parameters.addValue("disco", computer.getDiscontinued() == null ? null : new Timestamp(computer.getDiscontinued().getTime()), Types.TIMESTAMP);
	    parameters.addValue("idCompany", computer.getCompany() == null ? null : computer.getCompany().getId());

	    NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
	    if(vJdbcTemplate.update(UPDATE_COMPUTER_REQUEST, parameters) == 0) {
	    	throw new DAOException("can not update the computer "+computer.toString());
	    }
	}

	@Override
	public void delete(Long idComputer) throws DAOException {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", idComputer);

	    NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
	    if(vJdbcTemplate.update(DELETE_COMPUTER_REQUEST, parameters) == 0) {
	    	throw new DAOException("can not delete the computer with the id "+idComputer);
	    }
	}

	@Override
	public Computer find(Long idComputer) throws DAOException {
		try {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
		    parameters.addValue("id", idComputer);

		    NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		    return vJdbcTemplate.queryForObject(SELECT_COMPUTER_REQUEST, parameters, this);
		} catch (IncorrectResultSizeDataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not find the computer with the id "+idComputer);
		}
	}

	@Override
	public List<Computer> list() {
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());
	    return vJdbcTemplate.query(LIST_COMPUTER_REQUEST, this);
	}
	
	/**
	 * List the computer in the database by a group
	 * 
	 * @param start the first computer object to be listed
	 * @param size the number of computer to list
	 * @return the list of computer found
	 * @throws SQLException thrown if a problem occur during the communication
	 * @throws DAOException 
	 */
	public List<Computer> listWithPagination(int start, int size) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("start", start);
		parameters.addValue("size", size);
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
	    return vJdbcTemplate.query(LIST_COMPUTER_REQUEST+LIMIT_COMPUTER, parameters, this);
	}
	
	/**
	 * List the computer with the name which contain the name parameter
	 * 
	 * @param name the pattern of search
	 * @return the list of computer found with the name parameter
	 * @throws SQLException thrown if a problem occur during the communication
	 * @throws DAOException 
	 */
	public List<Computer> listByName(String name) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("name", "%"+name+"%");
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
	    return vJdbcTemplate.query(LIST_COMPUTER_BY_NAME_REQUEST, parameters, this);
	}
	
	/**
	 * List the computer with the name which contain the name parameter
	 * and restrict them by a group
	 * 
	 * @param name the pattern of search
	 * @param start the first computer object to be listed
	 * @param size the number of computer to list
	 * @return the list of computer found
	 * @throws SQLException thrown if a problem occur during the communication
	 * @throws DAOException 
	 */
	public List<Computer> listByNameWithPagination(String name, int start, int size) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("start", start);
		parameters.addValue("size", size);
		parameters.addValue("name", "%"+name+"%");
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
	    return vJdbcTemplate.query(LIST_COMPUTER_BY_NAME_REQUEST+LIMIT_COMPUTER, parameters, this);
	}
	
	@Override
	public int count() {
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());
	    return vJdbcTemplate.queryForObject(COUNT_COMPUTER_REQUEST, Integer.class);
	}

	@Override
	public Computer mapRow(ResultSet result, int rowNum) throws SQLException {
		try {
			Long idCompany = result.getLong("company_id");
			return new ComputerBuilder().setId(result.getLong("id"))
					.setName(result.getString("name"))
					.setIntroduced(result.getTimestamp("introduced"))
					.setDiscontinued(result.getTimestamp("discontinued"))
					.setCompany(idCompany == 0 ? null : companyDAO.find(idCompany))
					.build();
		} catch(DAOException e) {
			throw new SQLException();
		}
	}

}

package fr.excilys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.excilys.exceptions.DAOException;
import fr.excilys.logger.ELoggerMessage;
import fr.excilys.model.Company;
import fr.excilys.model.Company.CompanyBuilder;

/**
 * The DAO of the company object
 * 
 * @author Matheo
 */
@Repository
public class CompanyDAO extends DAO<Company> {

	private static final String SELECT_COMPANY_REQUEST = "SELECT id, name FROM company WHERE id = :id";
	private static final String LIST_COMPANY_REQUEST = "SELECT id, name FROM company";
	private static final String COUNT_COMPANY_REQUEST = "SELECT COUNT(id) AS count FROM company";
	private static final String DELETE_COMPANY_REQUEST = "DELETE FROM company WHERE id = :id";
	private static final String DELETE_COMPUTER_OF_COMPANY_REQUEST = "DELETE FROM computer WHERE company_id = :id";
	
	private Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private CompanyDAO() { }
	
	@Override
	public void insert(Company company) throws DAOException {
		throw new DAOException("insert company not yet implemented");
	}

	@Override
	public void update(Company company) throws DAOException {
		throw new DAOException("update company not yet implemented");
	}

	@Override
	public void delete(Long idCompany) throws DAOException {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("id", idCompany);

	    NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
	    if(vJdbcTemplate.update(DELETE_COMPUTER_OF_COMPANY_REQUEST, parameters) == 0) {
	    	throw new DAOException("can not delete the computer with the idCompany "+idCompany);
	    }
	    logger.info(ELoggerMessage.STATEMENT_EXECUTED.toString(), idCompany);
	    if(vJdbcTemplate.update(DELETE_COMPANY_REQUEST, parameters) == 0) {
	    	throw new DAOException("can not delete the company with the id "+idCompany);
	    }
	    logger.info(ELoggerMessage.STATEMENT_EXECUTED.toString(), idCompany);
	}

	@Override
	public Company find(Long idCompany) throws DAOException {
		try {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
		    parameters.addValue("id", idCompany);

		    NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		    return vJdbcTemplate.queryForObject(SELECT_COMPANY_REQUEST, parameters, this);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not find the company with the id "+idCompany);
		}
	}

	@Override
	public List<Company> list() {
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());
	    return vJdbcTemplate.query(LIST_COMPANY_REQUEST, this);
	}
	
	@Override
	public int count() {
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());
	    return vJdbcTemplate.queryForObject(COUNT_COMPANY_REQUEST, Integer.class);
	}

	@Override
	public Company mapRow(ResultSet result, int rowNum) throws SQLException {
		return new CompanyBuilder().setId(result.getLong("id")).setName(result.getString("name")).build();
	}

}

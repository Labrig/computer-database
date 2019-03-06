package fr.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import fr.excilys.exceptions.DAOException;
import fr.excilys.model.Company;
import fr.excilys.model.Company.CompanyBuilder;

/**
 * The DAO of the company object
 * 
 * @author Matheo
 */
@Repository
public class CompanyDAO extends DAO<Company> {

	private static final String SELECT_COMPANY_REQUEST = "SELECT id, name FROM company WHERE id = ?";
	private static final String LIST_COMPANY_REQUEST = "SELECT id, name FROM company";
	private static final String COUNT_COMPANY_REQUEST = "SELECT COUNT(id) AS count FROM company";
	private static final String DELETE_COMPANY_REQUEST = "DELETE FROM company WHERE id = ?";
	private static final String DELETE_COMPUTER_OF_COMPANY_REQUEST = "DELETE FROM computer WHERE company_id = ?";
	
	private Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private CompanyDAO() { super(); }
	
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
		try(Connection connect = getConnection();
				PreparedStatement statementComputers = connect.prepareStatement(DELETE_COMPUTER_OF_COMPANY_REQUEST);
				PreparedStatement statementCompany = connect.prepareStatement(DELETE_COMPANY_REQUEST);) {
			try {
				connect.setAutoCommit(false);
				statementComputers.setLong(1, idCompany);
				statementComputers.execute();
				logger.info("The statement "+statementComputers+" has been executed." );
				statementCompany.setLong(1, idCompany);
				statementCompany.execute();
				logger.info("The statement "+statementCompany+" has been executed." );
				connect.commit();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				connect.rollback();
				throw new DAOException("can not company the computer with the id "+idCompany);
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		}
	}

	@Override
	public Company find(Long idCompany) throws DAOException {
		try(Connection	connect = getConnection();
				PreparedStatement statement = connect.prepareStatement(SELECT_COMPANY_REQUEST);){
			statement.setLong(1, idCompany);	
			ResultSet result = statement.executeQuery();
			logger.info("The statement "+statement+" has been executed." );
			result.next();
			Company company = mapResultSet(result);
			return company;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not find the Company with the id "+idCompany);
		}
	}

	@Override
	public List<Company> list() throws DAOException {
		try(Connection connect = getConnection();
				PreparedStatement statement = connect.prepareStatement(LIST_COMPANY_REQUEST);) {
			List<Company> listCompany = new ArrayList<>();
			ResultSet result = statement.executeQuery();
			logger.info("The statement "+statement+" has been executed." );
			while(result.next()) {
				listCompany.add(mapResultSet(result));		
			}
			return listCompany;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not list the companies");
		}
	}
	
	@Override
	public int count() throws DAOException {
		try(Connection	connect = getConnection();
				PreparedStatement statement = connect.prepareStatement(COUNT_COMPANY_REQUEST);){
			ResultSet result = statement.executeQuery();
			logger.info("The statement "+statement+" has been executed." );
			result.next();
			return result.getInt("count");
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not count the companies");
		}
	}

	@Override
	public Company mapResultSet(ResultSet result) throws DAOException {
		try {
			return new CompanyBuilder().setId(result.getLong("id")).setName(result.getString("name")).build();
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not build the company with the gave ResultSet");
		}
	}

}

package fr.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.excilys.exceptions.DAOException;
import fr.excilys.logger.ELoggerMessage;
import fr.excilys.model.Computer;
import fr.excilys.model.Computer.ComputerBuilder;

/**
 * The DAO of the computer object
 * 
 * @author Matheo
 */
@Repository
public class ComputerDAO extends DAO<Computer> {
	
	private static final String INSERT_COMPUTER_REQUEST = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_COMPUTER_REQUEST = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String DELETE_COMPUTER_REQUEST = "DELETE FROM computer WHERE id = ?";
	private static final String SELECT_COMPUTER_REQUEST = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
	private static final String LIST_COMPUTER_REQUEST = "SELECT id, name, introduced, discontinued, company_id FROM computer";
	private static final String LIST_COMPUTER_BY_NAME_REQUEST = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE name LIKE ?";
	private static final String LIMIT_COMPUTER = " LIMIT ?, ?";
	private static final String COUNT_COMPUTER_REQUEST = "SELECT COUNT(id) AS count FROM computer";
	
	@Autowired
	private CompanyDAO companyDAO;
	
	private Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	private ComputerDAO() { }

	@Override
	public void insert(Computer computer) throws DAOException {
		try(Connection connect = getConnection();
				PreparedStatement statement = connect.prepareStatement(INSERT_COMPUTER_REQUEST);){
			statement.setString(1, computer.getName());
			statement.setTimestamp(2, computer.getIntroduced() == null ? null : new Timestamp(computer.getIntroduced().getTime()));
			statement.setTimestamp(3, computer.getDiscontinued() == null ? null : new Timestamp(computer.getDiscontinued().getTime()));
			statement.setObject(4, computer.getCompany() == null ? null : computer.getCompany().getId());
			statement.execute();
			logger.info(ELoggerMessage.STATEMENT_EXECUTED.toString(), statement);
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not insert the computer "+computer.toString());
		}
	}

	@Override
	public void update(Computer computer) throws DAOException {
		try(Connection	connect = getConnection();
				PreparedStatement statement = connect.prepareStatement(UPDATE_COMPUTER_REQUEST);){
			statement.setString(1, computer.getName());
			statement.setTimestamp(2, computer.getIntroduced() == null ? null : new Timestamp(computer.getIntroduced().getTime()));
			statement.setTimestamp(3, computer.getDiscontinued() == null ? null : new Timestamp(computer.getDiscontinued().getTime()));
			statement.setObject(4, computer.getCompany() == null ? null : computer.getCompany().getId());
			statement.setLong(5, computer.getId());
			statement.execute();
			logger.info(ELoggerMessage.STATEMENT_EXECUTED.toString(), statement);
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not update the computer "+computer.toString());
		}
	}

	@Override
	public void delete(Long idComputer) throws DAOException {
		try(Connection connect = getConnection();
				PreparedStatement statement = connect.prepareStatement(DELETE_COMPUTER_REQUEST);){
			statement.setLong(1, idComputer);
			statement.execute();
			logger.info(ELoggerMessage.STATEMENT_EXECUTED.toString(), statement);
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not delete the computer with the id "+idComputer);
		}
	}

	@Override
	public Computer find(Long idComputer) throws DAOException {
		try(Connection	connect = getConnection();
				PreparedStatement statement = connect.prepareStatement(SELECT_COMPUTER_REQUEST);){
			
			statement.setLong(1, idComputer);
			
			try(ResultSet result = statement.executeQuery();) {
				logger.info(ELoggerMessage.STATEMENT_EXECUTED.toString(), statement);
				if(result.next()) {
					return mapResultSet(result);
				} else {
					logger.warn(ELoggerMessage.NO_RESULSET_RETURNED.toString());
					throw new DAOException(ELoggerMessage.NO_RESULSET_RETURNED.toString());
				}
			}
			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not find the computer with the id "+idComputer);
		}
	}

	@Override
	public List<Computer> list() throws DAOException {
		try(Connection connect = getConnection();
				PreparedStatement statement = connect.prepareStatement(LIST_COMPUTER_REQUEST);
				ResultSet result = statement.executeQuery();){
			List<Computer> listComputer = new ArrayList<>();
			logger.info(ELoggerMessage.STATEMENT_EXECUTED.toString(), statement);
			while(result.next()) {
				listComputer.add(mapResultSet(result));		
			}
			return listComputer;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not list the computers");
		}
		
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
	public List<Computer> listWithPagination(int start, int size) throws DAOException {
		try(Connection connect = getConnection();
				PreparedStatement statement = connect.prepareStatement(LIST_COMPUTER_REQUEST+LIMIT_COMPUTER);){
			
			List<Computer> listComputer = new ArrayList<>();
			statement.setInt(1, start);
			statement.setInt(2, size);
			
			try(ResultSet result = statement.executeQuery();) {
				logger.info(ELoggerMessage.STATEMENT_EXECUTED.toString(), statement);
				while(result.next()) {
					listComputer.add(mapResultSet(result));		
				}
				return listComputer;
			}
			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not list the computers between "+start+" and "+(start+size));
		}
	}
	
	/**
	 * List the computer with the name which contain the name parameter
	 * 
	 * @param name the pattern of search
	 * @return the list of computer found with the name parameter
	 * @throws SQLException thrown if a problem occur during the communication
	 * @throws DAOException 
	 */
	public List<Computer> listByName(String name) throws DAOException {
		try(Connection connect = getConnection();
				PreparedStatement statement = connect.prepareStatement(LIST_COMPUTER_BY_NAME_REQUEST);){
			
			List<Computer> listComputer = new ArrayList<>();
			statement.setString(1, "%"+name+"%");
			
			try(ResultSet result = statement.executeQuery();) {
				logger.info(ELoggerMessage.STATEMENT_EXECUTED.toString(), statement);
				while(result.next()) {
					listComputer.add(mapResultSet(result));		
				}
				return listComputer;
			}
			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not list the computers with the name "+name);
		}
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
	public List<Computer> listByNameWithPagination(String name, int start, int size) throws DAOException {
		try(Connection connect = getConnection();
				PreparedStatement statement = connect.prepareStatement(LIST_COMPUTER_BY_NAME_REQUEST+LIMIT_COMPUTER);){
			
			List<Computer> listComputer = new ArrayList<>();
			statement.setString(1, "%"+name+"%");
			statement.setInt(2, start);
			statement.setInt(3, size);
			
			try(ResultSet result = statement.executeQuery();) {
				logger.info(ELoggerMessage.STATEMENT_EXECUTED.toString(), statement);
				while(result.next()) {
					listComputer.add(mapResultSet(result));		
				}
				return listComputer;
			}
			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not list the computers with the name "+name+" between "+start+" and "+(start+size));
		}
	}
	
	@Override
	public int count() throws DAOException {
		try(Connection	connect = getConnection();
				PreparedStatement statement = connect.prepareStatement(COUNT_COMPUTER_REQUEST);
				ResultSet result = statement.executeQuery();){
			logger.info(ELoggerMessage.STATEMENT_EXECUTED.toString(), statement);
			if(result.next()) {
				return result.getInt("count");
			} else {
				logger.warn(ELoggerMessage.NO_RESULSET_RETURNED.toString());
				throw new DAOException(ELoggerMessage.NO_RESULSET_RETURNED.toString());
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not count the computers");
		}
	}
	
	@Override
	public Computer mapResultSet(ResultSet result) throws DAOException {
		try {
			Long idCompany = result.getLong("company_id");
			return new ComputerBuilder().setId(result.getLong("id"))
					.setName(result.getString("name"))
					.setIntroduced(result.getTimestamp("introduced"))
					.setDiscontinued(result.getTimestamp("discontinued"))
					.setCompany(idCompany == 0 ? null : companyDAO.find(idCompany))
					.build();
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DAOException("can not build the computer with the gave ResultSet");
		}
	}

}

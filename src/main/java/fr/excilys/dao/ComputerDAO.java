package fr.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.model.Computer;
import fr.excilys.validator.ComputerValidator;

public class ComputerDAO implements DAO<Computer> {
	
	private static final String INSERT_COMPUTER_REQUEST = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_COMPUTER_REQUEST = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String DELETE_COMPUTER_REQUEST = "DELETE FROM computer WHERE id = ?";
	private static final String SELECT_COMPUTER_REQUEST = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
	private static final String LIST_COMPUTER_REQUEST = "SELECT id, name, introduced, discontinued, company_id FROM computer";

	private static ComputerDAO instance = new ComputerDAO();
	
	private ComputerDAO() { }
	
	public static ComputerDAO getInstance() {
		return instance;
	}

	@Override
	public void insert(Computer computer) throws SQLException {
		ComputerValidator.getInstance().attributeNotNull(computer);
		ComputerValidator.getInstance().attributeNotNull(computer.getName());
		try(Connection connect = DAOFactory.getConnection();
				PreparedStatement statement = connect.prepareStatement(INSERT_COMPUTER_REQUEST);){
			statement.setString(1, computer.getName());
			statement.setTimestamp(2, computer.getIntroduced() == null ? null : new Timestamp(computer.getIntroduced().getTime()));
			statement.setTimestamp(3, computer.getDiscontinued() == null ? null : new Timestamp(computer.getDiscontinued().getTime()));
			statement.setObject(4, computer.getCompany() == null ? null : computer.getCompany().getId());
			statement.execute();
		}
	}

	@Override
	public void update(Computer computer) throws SQLException {
		ComputerValidator.getInstance().attributeNotNull(computer);
		ComputerValidator.getInstance().attributeNotNull(computer.getId());
		try(Connection	connect = DAOFactory.getConnection();
				PreparedStatement statement = connect.prepareStatement(UPDATE_COMPUTER_REQUEST);){
			statement.setString(1, computer.getName());
			statement.setTimestamp(2, computer.getIntroduced() == null ? null : new Timestamp(computer.getIntroduced().getTime()));
			statement.setTimestamp(3, computer.getDiscontinued() == null ? null : new Timestamp(computer.getDiscontinued().getTime()));
			statement.setObject(4, computer.getCompany() == null ? null : computer.getCompany().getId());
			statement.setLong(5, computer.getId());
			statement.execute();
		}
	}

	@Override
	public void delete(Long idComputer) throws SQLException {
		ComputerValidator.getInstance().attributeNotNull(idComputer);
		try(Connection connect = DAOFactory.getConnection();
				PreparedStatement statement = connect.prepareStatement(DELETE_COMPUTER_REQUEST);){
			statement.setLong(1, idComputer);
			statement.execute();
		}
	}

	@Override
	public Computer find(Long idComputer) throws SQLException {
		ComputerValidator.getInstance().attributeNotNull(idComputer);
		Computer computer = new Computer();
		try(Connection	connect = DAOFactory.getConnection();
				PreparedStatement statement = connect.prepareStatement(SELECT_COMPUTER_REQUEST);){
			statement.setLong(1, idComputer);	
			ResultSet result = statement.executeQuery();		
			result.next();
			computer = mapResultSet(result);
		}
		return computer;
	}

	@Override
	public List<Computer> list() throws SQLException {
		List<Computer> listComputer = new ArrayList<>();
		try(Connection connect = DAOFactory.getConnection();
				PreparedStatement statement = connect.prepareStatement(LIST_COMPUTER_REQUEST);){
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				listComputer.add(mapResultSet(result));		
			}
		}
		return listComputer;
	}
	
	@Override
	public Computer mapResultSet(ResultSet result) throws SQLException {
		Computer computer = new Computer();
		computer.setId(result.getLong("id"));
		computer.setName(result.getString("name"));
		computer.setIntroduced(result.getTimestamp("introduced"));
		computer.setDiscontinued(result.getTimestamp("discontinued"));
		Long idComapny = result.getLong("company_id");
		computer.setCompany(idComapny == 0 ? null : CompanyDAO.getInstance().find(idComapny));
		return computer;
	}

}

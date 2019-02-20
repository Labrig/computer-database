package fr.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.model.Company;

public class CompanyDAO implements DAO<Company> {

	private static final String SELECT_COMPANY_REQUEST = "SELECT id, name FROM company WHERE id = ?";
	private static final String LIST_COMPANY_REQUEST = "SELECT id, name FROM company";
	
	private static CompanyDAO instance = new CompanyDAO();
	
	private CompanyDAO() { }
	
	public static CompanyDAO getInstance() {
		return instance;
	}
	
	@Override
	public void insert(Company company) throws SQLException {
		throw new SQLException("insert company not yet implemented");
	}

	@Override
	public void update(Company company) throws SQLException {
		throw new SQLException("update company not yet implemented");
	}

	@Override
	public void delete(Long idCompany) throws SQLException {
		throw new SQLException("delete company not yet implemented");
	}

	@Override
	public Company find(Long idCompany) throws SQLException {
		Company company = new Company();
		try(Connection	connect = DAOFactory.getConnection();
				PreparedStatement statement = connect.prepareStatement(SELECT_COMPANY_REQUEST);){
			statement.setLong(1, idCompany);	
			ResultSet result = statement.executeQuery();		
			result.next();
			company = mapResultSet(result);
		}
		return company;
	}

	@Override
	public List<Company> list() throws SQLException {
		List<Company> listCompany = new ArrayList<>();
		try(Connection connect = DAOFactory.getConnection();
				PreparedStatement statement = connect.prepareStatement(LIST_COMPANY_REQUEST);) {
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				listCompany.add(mapResultSet(result));		
			}
		}
		return listCompany;
	}

	@Override
	public Company mapResultSet(ResultSet result) throws SQLException {
		Company company = new Company();
		company.setId(result.getLong("id"));
		company.setName(result.getString("name"));
		return company;
	}

}

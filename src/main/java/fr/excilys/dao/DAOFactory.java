package fr.excilys.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {
	
	private static final String URL="jdbc:mysql://localhost/computer-database-db";
	private static final String USERNAME="admincdb";
	private static final String PASSWORD="qwerty1234";
	private static final String DRIVER="com.mysql.cj.jdbc.Driver";
	
	private static DAOFactory instance = new DAOFactory();

	private DAOFactory(){
		try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	public static DAOFactory getInstance() {
		return instance;
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL,USERNAME,PASSWORD);
	}
	
	public CompanyDAO getCompanyDAO() {
		return CompanyDAO.getInstance();
	}
	
	public ComputerDAO getComputerDAO() {
		return ComputerDAO.getInstance();
	}
}

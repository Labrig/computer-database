package fr.excilys.validator;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

public class ComputerValidator {

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private static ComputerValidator instance = new ComputerValidator();
	
	private ComputerValidator() { }
	
	public static ComputerValidator getInstance() {
		return instance;
	}

	public void verifyId(Computer computer, String id) throws ComputerFormatException {
		try {
			computer.setId(Long.valueOf(id));
		} catch(NumberFormatException e) {
			throw new ComputerFormatException("id is not a number");
		}
	}
	
	public void verifyIntro(Computer computer, String intro) throws ComputerFormatException {
		try {
			computer.setIntroduced(formatter.parse(intro));
		} catch(ParseException e) {
			throw new ComputerFormatException("the date is not at format yyyy-MM-dd");
		}
	}
	
	public void verifyDisco(Computer computer, String disco) throws ComputerFormatException {
		try {
			computer.setDiscontinued(formatter.parse(disco));
		} catch(ParseException e) {
			throw new ComputerFormatException("the date is not at format yyyy-MM-dd");
		}
	}
	
	public void verifyIdCompany(Computer computer, String idCompany) throws ComputerFormatException {
		try {
			if(Long.valueOf(idCompany) != 0) {
				Company company = new Company();
				company.setId(Long.valueOf(idCompany));
				computer.setCompany(company);
			}
		} catch(NumberFormatException e) {
			throw new ComputerFormatException("idCompany is not a number");
		}
	}
	
	public void verifyIntroBeforeDisco(Computer computer) throws ComputerFormatException {
		if(computer.getDiscontinued() != null && (computer.getIntroduced() == null || !computer.getIntroduced().before(computer.getDiscontinued()))) {
			throw new ComputerFormatException("the discontinued date is before the introduction");
		}
	}
	
	public void attributeNotNull(Object object) throws SQLException {
		if(object == null) {
			throw new SQLException();
		}
	}
}

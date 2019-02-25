package fr.excilys.validator;

import java.sql.SQLException;

import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.model.Computer;

public class ComputerValidator {

	private static ComputerValidator instance = new ComputerValidator();
	
	private ComputerValidator() { }
	
	public static ComputerValidator getInstance() {
		return instance;
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

package fr.excilys.validator;

import java.sql.SQLException;

import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.model.Computer;

/**
 * Singleton use to validate the integrity of the computer
 * before persist him in the database
 * 
 * @author Matheo
 */
public class ComputerValidator {

	private static ComputerValidator instance = new ComputerValidator();
	
	private ComputerValidator() { }
	
	public static ComputerValidator getInstance() {
		return instance;
	}
	
	/**
	 * Check if the discontinued date is before the introduction one
	 * 
	 * @param computer the computer to check
	 * @throws ComputerFormatException thrown if the discontinued date is before the introduction one
	 */
	public void verifyIntroBeforeDisco(Computer computer) throws ComputerFormatException {
		if(computer.getDiscontinued() != null && (computer.getIntroduced() == null || !computer.getIntroduced().before(computer.getDiscontinued()))) {
			throw new ComputerFormatException("the discontinued date is before the introduction");
		}
	}
	
	/**
	 * Check if an object gave is not null before persist him in the database
	 * 
	 * @param object the object to check
	 * @throws SQLException thrown if the gave object is null
	 */
	public void attributeNotNull(Object object) throws SQLException {
		if(object == null) {
			throw new SQLException();
		}
	}
}

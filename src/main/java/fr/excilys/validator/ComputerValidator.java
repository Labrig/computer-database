package fr.excilys.validator;

import java.sql.SQLException;

import fr.excilys.exceptions.ValidationException;
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
	
	public void verifyIdNotNull(Long id) throws ValidationException {
		if(id == null || id == 0) {
			throw new ValidationException("the id is null or zero");
		}
	}
	
	public void verifyName(String name) throws ValidationException {
		if(name == null || name.isEmpty()) {
			throw new ValidationException("the name is null or empty");
		}
	}
	
	/**
	 * Check if the discontinued date is before the introduction one
	 * 
	 * @param computer the computer to check
	 * @throws ValidationException thrown if the discontinued date is before the introduction one
	 */
	public void verifyIntroBeforeDisco(Computer computer) throws ValidationException {
		if(computer.getDiscontinued() != null && (computer.getIntroduced() == null || !computer.getIntroduced().before(computer.getDiscontinued()))) {
			throw new ValidationException("the discontinued date is before the introduction");
		}
	}
	
	/**
	 * Check if an object gave is not null before persist him in the database
	 * 
	 * @param object the object to check
	 * @throws SQLException thrown if the gave object is null
	 */
	public void verifyComputerNotNull(Computer computer) throws ValidationException {
		if(computer == null) {
			throw new ValidationException("the computer is null");
		}
	}
}

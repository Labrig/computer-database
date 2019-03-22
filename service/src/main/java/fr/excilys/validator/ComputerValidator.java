package fr.excilys.validator;

import java.sql.SQLException;

import org.springframework.stereotype.Component;

import fr.excilys.exception.ComputerDiscoBeforeIntroException;
import fr.excilys.exception.ComputerIdNullException;
import fr.excilys.exception.ComputerNameNullException;
import fr.excilys.exception.ComputerValidationException;
import fr.excilys.model.Computer;

/**
 * Singleton use to validate the integrity of the computer
 * before persist him in the database
 * 
 * @author Matheo
 */
@Component
public class ComputerValidator {
	
	private ComputerValidator() { }
	
	public void verifyIdNotNull(Long id) throws ComputerValidationException {
		if(id == null || id == 0) {
			throw new ComputerIdNullException();
		}
	}
	
	public void verifyName(String name) throws ComputerValidationException {
		if(name == null || name.isEmpty()) {
			throw new ComputerNameNullException();
		}
	}
	
	/**
	 * Check if the discontinued date is before the introduction one
	 * 
	 * @param computer the computer to check
	 * @throws ComputerValidationException thrown if the discontinued date is before the introduction one
	 */
	public void verifyIntroBeforeDisco(Computer computer) throws ComputerValidationException {
		if(computer.getDiscontinued() != null && (computer.getIntroduced() == null || !computer.getIntroduced().before(computer.getDiscontinued()))) {
			throw new ComputerDiscoBeforeIntroException();
		}
	}
	
	/**
	 * Check if an object gave is not null before persist him in the database
	 * 
	 * @param object the object to check
	 * @throws SQLException thrown if the gave object is null
	 */
	public void verifyComputerNotNull(Computer computer) throws ComputerValidationException {
		if(computer == null) {
			throw new ComputerValidationException();
		}
	}
}

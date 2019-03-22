package fr.excilys.exception;

public class ComputerNameNullException extends ComputerValidationException {

	private static final long serialVersionUID = 1L;
	
	public ComputerNameNullException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "The computer name is null or empty";
	}
}

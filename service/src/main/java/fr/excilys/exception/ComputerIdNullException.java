package fr.excilys.exception;

public class ComputerIdNullException extends ComputerValidationException {

	private static final long serialVersionUID = 1L;
	
	public ComputerIdNullException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "The computer is null or equals to 0";
	}
}

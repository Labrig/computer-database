package fr.excilys.exception;

public class ComputerIdFormatException extends ComputerMappingException {

	private static final long serialVersionUID = 1L;
	
	public ComputerIdFormatException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "The computer id is not a long";
	}
}

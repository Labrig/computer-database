package fr.excilys.exceptions.mapping;

public class ComputerDateFormatException extends ComputerMappingException {

	private static final long serialVersionUID = 1L;
	
	public ComputerDateFormatException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "The computer date is not to the format yyyy-MM-dd";
	}
}

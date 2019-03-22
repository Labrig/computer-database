package fr.excilys.exception;

public class ComputerMappingException extends MappingException {

	private static final long serialVersionUID = 1L;
	
	public ComputerMappingException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "The computer could not be mapped with these parameters";
	}
}

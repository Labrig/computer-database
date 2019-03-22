package fr.excilys.exception;

public class MappingException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public MappingException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "A mapping exception occured";
	}
}

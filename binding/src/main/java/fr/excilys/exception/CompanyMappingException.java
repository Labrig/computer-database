package fr.excilys.exception;

public class CompanyMappingException extends MappingException {

	private static final long serialVersionUID = 1L;
	
	public CompanyMappingException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "The company could not be mapped with these parameters";
	}
}

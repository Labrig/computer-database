package fr.excilys.exceptions.mapping;

public class CompanyIdFormatException extends CompanyMappingException {

	private static final long serialVersionUID = 1L;
	
	public CompanyIdFormatException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "The company id is not a long";
	}
}

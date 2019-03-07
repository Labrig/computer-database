package fr.excilys.exceptions.mapping;

public class ComputerIdCompanyFormatException extends ComputerMappingException {

	private static final long serialVersionUID = 1L;
	
	public ComputerIdCompanyFormatException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "The computer id company is not a long";
	}
}

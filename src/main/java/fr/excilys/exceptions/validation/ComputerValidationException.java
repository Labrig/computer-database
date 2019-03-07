package fr.excilys.exceptions.validation;

public class ComputerValidationException extends ValidationException {

	private static final long serialVersionUID = 1L;
	
	public ComputerValidationException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "The integrity of th computer is not good";
	}
}

package fr.excilys.exception;

public class ComputerDiscoBeforeIntroException extends ComputerValidationException {

	private static final long serialVersionUID = 1L;
	
	public ComputerDiscoBeforeIntroException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "The introduction date is after the discontuned date";
	}
}

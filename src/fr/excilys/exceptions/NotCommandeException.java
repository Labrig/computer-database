package fr.excilys.exceptions;

/**
 * Exception class invoked when a user command
 * is not standard
 * @author Matheo
 */
public class NotCommandeException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotCommandeException(String message) {
		super(message);
	}
}

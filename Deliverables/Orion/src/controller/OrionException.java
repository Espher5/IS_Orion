package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/OrionException")
public class OrionException extends ServletException {
	private static final long serialVersionUID = 7725175497276510923L;

	public OrionException() {
		super();
	}

	public OrionException(String message) {
		super(message);
	}

	public OrionException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

	public OrionException(Throwable rootCause) {
		super(rootCause);
	}
}
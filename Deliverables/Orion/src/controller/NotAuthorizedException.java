package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/NotAutorizedException")
public class NotAuthorizedException extends ServletException{

	private static final long serialVersionUID = 1L;

    public NotAuthorizedException() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see ServletException#ServletException(String)
     */
    public NotAuthorizedException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see ServletException#ServletException(String, Throwable)
     */
    public NotAuthorizedException(String message, Throwable rootCause) {
        super(message, rootCause);
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see ServletException#ServletException(Throwable)
     */
    public NotAuthorizedException(Throwable rootCause) {
        super(rootCause);
        // TODO Auto-generated constructor stub
    }
}
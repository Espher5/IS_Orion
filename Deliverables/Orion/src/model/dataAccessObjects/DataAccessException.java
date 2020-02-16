package model.dataAccessObjects;

/**
 * Eccezione non controllata per gestire un errore generico nelle operazioni
 * di accesso ai dati
 * 
 * @author Michelangelo Esposito
 *
 */
public class DataAccessException extends RuntimeException {
	private static final long serialVersionUID = -3451942046943341128L;

	public DataAccessException() {
		
	}
	
	public DataAccessException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public DataAccessException(final String message) {
		super(message);
	}
	
	public DataAccessException(final Throwable cause) {
		super(cause);
	}
}

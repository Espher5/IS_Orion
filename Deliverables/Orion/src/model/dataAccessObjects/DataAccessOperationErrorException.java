package model.dataAccessObjects;

/**
 * Eccezione non controllata più specifica per gestire un errore
 *  nelle operazioni di accesso ai dati
 * 
 * @author Michelangelo Esposito
 *
 */
public class DataAccessOperationErrorException extends DataAccessException {
	private static final long serialVersionUID = 976030953624684459L;

	public DataAccessOperationErrorException() {
		
	}
	
	public DataAccessOperationErrorException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public DataAccessOperationErrorException(final String message) {
		super(message);
	}
	
	public DataAccessOperationErrorException(final Throwable cause) {
		super(cause);
	}
}

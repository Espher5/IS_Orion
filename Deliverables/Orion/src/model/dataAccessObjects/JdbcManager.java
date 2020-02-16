package model.dataAccessObjects;

import java.util.List;

/**
 * Interfaccia in cui sono specificati i metodi
 * generali per l'accesso ai dati persistenti
 * 
 * @author Michelangelo Esposito
 *
 */
public interface JdbcManager {
	/**
	 * Effettua un'operazione di inseriemnto dati nel
	 * database mediante query parametrica;
	 * 
	 * @param query la query da effettuare
	 * @param parameters lista di parametri da inserire nella query
	 * @return un intero che rappresenta l'esito dell'operazione
	 * @throws DataAccessException nel caso di errori nell'utilizzo dei parametri,
	 * nella formulazione della query o nelle operazioni a carico del DBMS
	 */
	int doSave(String query, Object... parameters) throws DataAccessException;
	
	
	/**
	 * Effettua un'operazione di inseriemnto dati nel
	 * database mediante query parametrica e restituisce
	 * la chiava primaria autogenerata dal DBMS;
	 * 
	 * @param query la query da effettuare
	 * @param parameters lista di parametri da inserire nella query
	 * @return un intero che rappresenta l'esito dell'operazione
	 * @throws DataAccessException nel caso di errori nell'utilizzo dei parametri,
	 * nella formulazione della query o nelle operazioni a carico del DBMS
	 */
	long doSaveWithGeneratedValues(final String query, final Object... parameters) throws DataAccessException;
	
	
	/**
	 * Effettua un'operazione di aggiornamento dati nel
	 * database mediante query parametrica;
	 * 
	 * @param query la query da effettuare
	 * @param parameters lista di parametri da inserire nella query
	 * @return un intero che rappresenta il numero di tuple affette dall'operazione
	 * @throws DataAccessException nel caso di errori nell'utilizzo dei parametri,
	 * nella formulazione della query o nelle operazioni a carico del DBMS
	 */
	int doUpdate(final String query, final Object... parameters) throws DataAccessException;
		
	
	/**
	 * Effettua un'operazione di rimozione dati nel
	 * database mediante query parametrica;
	 * 
	 * @param query la query da effettuare
	 * @param parameters lista di parametri da inserire nella query
	 * @return 
	 * @throws DataAccessException nel caso di errori nell'utilizzo dei parametri,
	 * nella formulazione della query o nelle operazioni a carico del DBMS
	 */
	int doDelete(final String query, final Object... parameters) throws DataAccessException;
		
	
	/**
	 * Recupera le tuple dal database che rispettano i parametri passati
	 * ed incapsula i risultati in una lista
	 * 
	 * @param query la query da effettuare
	 * @param rowMapper 
	 * @param parameters lista di parametri da inserire nella query
	 * @return la lista recuperata o null nel caso di nessun risultato
	 * @throws DataAccessException nel caso di errori nell'utilizzo dei parametri,
	 * nella formulazione della query o nelle operazioni a carico del DBMS
	 */
	<E> List<E> doSelect(final String query, final RowMapper<E> rowMapper, 
			final Object... parameters) throws DataAccessException;
}

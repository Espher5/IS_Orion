package model.dataAccessObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe per la gestione ed il riutilizzo
 * delle connessioni al database
 * 
 * @author Michelangelo Esposito
 *
 */
public class DBConnectionPool {
	private static List<Connection> freeDBConnections;

	/**
	 * Inizializza una lista di connessioni
	 * e carica il driver JDBC per MySQL
	 */
	static {
		freeDBConnections = new LinkedList<Connection>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("DB driver not found: " + e.getMessage());
		} 
	}
	
	/**
	 * Crea una nuova connessione al database, utilizzando 
	 * indirizzo IP, nome del database, esername e password
	 * 
	 * @return
	 * @throws SQLException in caso di errore durante la gestione della connessione
	 */
	private static synchronized Connection createDBConnection() throws SQLException {
		Connection newConnection = null;
		String ip = "localhost";
		String db = "Orion";
		String username = "root";
		String password = "Tastiera45";
		newConnection = DriverManager.getConnection("jdbc:mysql://" 
					  + ip + "/" + db 
					  + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", 
					  username, password);
		return newConnection;
	}

	/**
	 * Restituisce una connessione dalla lista o,
	 * se questa è vuota, ne crea una nuova
	 * 
	 * @return la connessione
	 * @throws SQLException in caso di errore durante la gestione della connessione
	 */
	public static synchronized Connection getConnection() throws SQLException {
		Connection connection;

		if (!freeDBConnections.isEmpty()) {
			connection = (Connection) freeDBConnections.get(0);
			freeDBConnections.remove(0);
			
			try {
				if (connection.isClosed())
					connection = getConnection();
			} catch (SQLException e) {
				connection.close();
				connection = getConnection();
			}
		} 
		else {
			connection = createDBConnection();		
		}
		return connection;
	}

	/**
	 * Aggiunge la connessione nella lista, senza rilasciarla
	 * 
	 * @param connection la connessione da aggiungere alla lista
	 * @throws SQLException SQLException in caso di errore durante la gestione della connessione
	 */
	public static synchronized void releaseConnection(Connection connection) throws SQLException {
		if(connection != null) freeDBConnections.add(connection);
	}
		
	/**
	 * Chiude tutte le connessioni della lista
	 * 
	 * @throws SQLException SQLException in caso di errore durante la gestione della connessione
	 */
	public static synchronized void releaseConnections() throws SQLException {
		for(Connection c: freeDBConnections) {
			c.close();
		}
	}
}
package model.dataAccessObjects;

/**
 * Classe astratta estesa dai DAO; incapsula
 * l'oggetto jdbcManager con cui effettuare le
 * operazioni di accesso al database
 *
 * @author Michelangelo Esposito
 *
 */
public abstract class AbstractDao {
	private JdbcManager jdbcManager;
	
	public AbstractDao(JdbcManager jdbcManager) {
		this.jdbcManager = jdbcManager;
	}
	protected JdbcManager getJdbcManager() {
		return jdbcManager;
	}
}

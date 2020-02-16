package model.dataAccessObjects.utenza;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;

public class UtenteDaoImpl extends AbstractDao implements UtenteDao {
	public UtenteDaoImpl() {
		super(new JdbcManagerImpl());
	}

	public static final RowMapper<UtenteBean> UTENTE_MAPPER = new RowMapper<UtenteBean>() {
		@Override
		public UtenteBean map(final ResultSet resultSet) throws SQLException {
			final UtenteBean utente = new UtenteBean();
			utente.setIdUtente(resultSet.getLong("ID_utente"));
			utente.setEmail(resultSet.getString("email"));
			utente.setPassword(resultSet.getString("password_utente"));
			utente.setNome(resultSet.getString("nome"));
			utente.setCognome(resultSet.getString("cognome"));
			utente.setDataRegistrazione(new Date(resultSet.getDate("data_registrazione").getTime()));
			utente.setStato(resultSet.getBoolean("stato"));
			return utente;
		}
	};
	
	@Override
	public long doSave(UtenteBean utente) {
		String query = "INSERT INTO Utente (email, password_utente, nome, cognome, data_registrazione, stato) "
					 + "VALUES (?, ?, ?, ?, ?, ?);";
		Object[] parameters = {
			utente.getEmail(),
			utente.getPassword(),
			utente.getNome(),
			utente.getCognome(),
			utente.getDataRegistrazione(),
			utente.getStato()
		};
		return getJdbcManager().doSaveWithGeneratedValues(query, parameters);
	}

	@Override
	public int doUpdate(UtenteBean utente) {
		String query = "UPDATE UTENTE "
					 + "SET email = ?, "
					 + "password_utente = ?, "
					 + "nome = ?, "
					 + "cognome = ?, "
					 + "data_registrazione = ?, "
					 + "stato = ? "
					 + "WHERE ID_utente = ?; ";
		Object[] parameters = {
				utente.getEmail(),
				utente.getPassword(),
				utente.getNome(),
				utente.getCognome(),
				utente.getDataRegistrazione(),
				utente.getStato(),
				utente.getIdUtente()
		};
		return getJdbcManager().doUpdate(query, parameters);
	}

	@Override
	public int doDelete(long idUtente) {
		String query = "DELETE FROM Utente "
					 + "WHERE ID_utente = ?;";
		return getJdbcManager().doDelete(query, idUtente);
	}

	@Override
	public UtenteBean doRetrieveByKey(long idUtente)  {
		String query = "SELECT * "
					 + "FROM Utente "
					 + "WHERE ID_utente = ?;";
		List<UtenteBean> result = getJdbcManager().doSelect(query, UTENTE_MAPPER, idUtente);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public UtenteBean doRetrieveByEmail(String email) {
		String query = "SELECT * "
					 + "FROM Utente "
					 + "WHERE email = ?;";
		List<UtenteBean> result = getJdbcManager().doSelect(query, UTENTE_MAPPER, email);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<UtenteBean> doRetrieveAll(int limit, int offset) {
		String query = "SELECT * "
					 + "FROM Utente "
					 + "LIMIT ? OFFSET ?;";
		List<UtenteBean> result = getJdbcManager().doSelect(query, UTENTE_MAPPER, limit, offset);
		return result.isEmpty() ? null : result;	
	}
}

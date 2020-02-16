package model.dataAccessObjects.utenza;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.beans.utenza.AmministratoreBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;

public class AmministratoreDaoImpl extends AbstractDao implements AmministratoreDao {

	public AmministratoreDaoImpl() {
		super(new JdbcManagerImpl());
	}
	
	public static final RowMapper<AmministratoreBean> AMMINISTRATORE_MAPPER = new RowMapper<AmministratoreBean>() {
		@Override
		public AmministratoreBean map(final ResultSet resultSet) throws SQLException {
			final AmministratoreBean amministratore = new AmministratoreBean();
			amministratore.setIdUtente(resultSet.getLong("ID_utente"));
			amministratore.setNumInserzioniRevisionate(resultSet.getInt("num_revisionate"));
			return amministratore;
		}
	};

	@Override
	public int doSave(AmministratoreBean amministratore) {
		String query = "INSERT INTO Amministratore "
					 + "VALUES (?, ?);";
		Object[] parameters = {
			amministratore.getIdUtente(),
			amministratore.getNumInserzioniRevisionate()
		};	
		return getJdbcManager().doSave(query, parameters);
	}

	@Override
	public int doUpdate(AmministratoreBean amministratore) {
		String query = "UPDATE Amministratore "
					 + "SET num_revisionate = ? "
					 + "WHERE ID_utente = ?;";
		Object[] parameters = {
			amministratore.getNumInserzioniRevisionate(),
			amministratore.getIdUtente()
		};	
		return getJdbcManager().doSave(query, parameters);
	}

	@Override
	public int doDelete(long idUtente) {
		String query = "DELETE FROM Amministratore "
				 	 + "WHERE ID_utente = ?;";
		return getJdbcManager().doSave(query, idUtente);
	}

	@Override
	public AmministratoreBean doRetrieveByKey(long idUtente) {
		String query = "SELECT * "
				 	 + "FROM Amministratore "
				 	 + "WHERE ID_utente = ?;";
		final List<AmministratoreBean> result = getJdbcManager().doSelect(query, AMMINISTRATORE_MAPPER, idUtente);
		return result.isEmpty() ? null : result.get(0);
	}
}

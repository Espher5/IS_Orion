package model.dataAccessObjects.utenza;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.beans.utenza.ProprietarioBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;


public class ProprietarioDaoImpl extends AbstractDao implements ProprietarioDao {
	public ProprietarioDaoImpl() {
		super(new JdbcManagerImpl());
	}

	public static final RowMapper<ProprietarioBean> PROPRIETARIO_MAPPER = new RowMapper<ProprietarioBean>() {
		@Override
		public ProprietarioBean map(final ResultSet resultSet) throws SQLException {
			final ProprietarioBean proprietario = new ProprietarioBean();
			proprietario.setIdUtente(resultSet.getLong("ID_utente"));
			proprietario.setCodiceFiscale(resultSet.getString("codice_fiscale"));
			proprietario.setNumInserzioniInserite(resultSet.getInt("num_inserzioni"));
			return proprietario;
		}
	};
	
	@Override
	public int doSave(ProprietarioBean proprietario) {
		String query = "INSERT INTO Proprietario "
					 + "VALUES(?, ?, ?);";
		Object[] parameters = {
			proprietario.getIdUtente(),
			proprietario.getCodiceFiscale(),
			proprietario.getNumInserzioniInserite(),
		};
		return getJdbcManager().doSave(query, parameters);
	}

	@Override
	public int doUpdate(ProprietarioBean proprietario) {
		String query = "UPDATE Proprietario "
					 + "SET codice_fiscale = ?, "
					 + "num_inserzioni = ? "
					 + "WHERE ID_utente = ?;";
		Object[] parameters = {
				proprietario.getCodiceFiscale(),
				proprietario.getNumInserzioniInserite(),
				proprietario.getIdUtente()
			};
		return getJdbcManager().doUpdate(query, parameters);
	}

	@Override
	public int doDelete(long idUtente) {
		String query = "DELETE FROM Proprietario "
					 + "WHERE ID_utente = ?;";
		return getJdbcManager().doDelete(query, idUtente);
	}

	@Override
	public ProprietarioBean doRetrieveByKey(long idUtente) {
		String query = "SELECT * "
					 + "FROM Proprietario "
					 + "WHERE ID_utente = ?;";
		final List<ProprietarioBean> result = getJdbcManager().doSelect(query, PROPRIETARIO_MAPPER, idUtente);
		return result.isEmpty() ? null : result.get(0);
	}
}

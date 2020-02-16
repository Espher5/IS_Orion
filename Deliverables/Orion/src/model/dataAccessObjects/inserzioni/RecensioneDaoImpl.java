package model.dataAccessObjects.inserzioni;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.beans.inserzioni.RecensioneBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;

public class RecensioneDaoImpl extends AbstractDao implements RecensioneDao {
	public RecensioneDaoImpl() {
		super(new JdbcManagerImpl());
	}

	public static final RowMapper<RecensioneBean> RECENSIONE_MAPPER = new RowMapper<RecensioneBean>() {
		@Override
		public RecensioneBean map(ResultSet resultSet) throws SQLException {
			RecensioneBean recensione = new RecensioneBean();
			recensione.setIdRecensione(resultSet.getLong("ID_recensione"));
			recensione.setIdCliente(resultSet.getLong("ID_cliente"));
			recensione.setIdInserzione(resultSet.getLong("ID_inserzione"));
			recensione.setPunteggio(resultSet.getInt("punteggio"));
			recensione.setTitolo(resultSet.getString("titolo"));
			recensione.setContenuto(resultSet.getString("contenuto"));
			recensione.setDataPubblicazione(resultSet.getDate("data_pubblicazione"));
			return recensione;
		}
	};
	
	@Override
	public long doSave(RecensioneBean recensione) {
		String query = "INSERT INTO Recensione (ID_cliente, ID_inserzione, "
					 + "punteggio, titolo, contenuto, data_pubblicazione) "
					 + "VALUES (?, ?, ?, ?, ?, ?);";
		Object[] parameters = {
			recensione.getIdCliente(),
			recensione.getIdInserzione(),
			recensione.getPunteggio(),
			recensione.getTitolo(),
			recensione.getContenuto(),
			recensione.getDataPubblicazione()
		};
		return getJdbcManager().doSaveWithGeneratedValues(query, parameters);
	}

	@Override
	public int doUpdate(RecensioneBean recensione) {
		String query = "UPDATE Recensione "
					 + "SET punteggio = ?, "
					 + "titolo = ?, "
					 + "contenuto = ?, "
					 + "WHERE ID_recensione = ?;";
		Object[] parameters = {
			recensione.getPunteggio(),
			recensione.getTitolo(),
			recensione.getContenuto(),
			recensione.getIdRecensione()
		};
		return getJdbcManager().doUpdate(query, parameters);
	}

	@Override
	public int doDelete(long idRecensione) {
		String query = "DELETE FROM Recensione "
					 + "WHERE ID_recensione = ?;";
		return getJdbcManager().doDelete(query, idRecensione);
	}

	@Override
	public RecensioneBean doRetrieveByKey(long idRecensione) {
		String query = "SELECT * "
					 + "FROM Recensione "
					 + "WHERE ID_recensione = ?;";
		List<RecensioneBean> result = getJdbcManager().doSelect(query, RECENSIONE_MAPPER, idRecensione);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public RecensioneBean doRetrieveByIdClienteAndIdInserzione(long idCliente, long idInserzione) {
		String query = "SELECT * "
					 + "FROM Recensione "
					 + "WHERE ID_cliente = ? "
					 + "AND ID_inserzione = ?;";
		List<RecensioneBean> result = getJdbcManager().doSelect(query, RECENSIONE_MAPPER, idCliente, idInserzione);
		return result.isEmpty() ? null : result.get(0);
	}
	
	@Override
	public List<RecensioneBean> doRetrieveByIdInserzione(long idInserzione) {
		String query = "SELECT * "
					 + "FROM Recensione "
					 + "WHERE ID_inserzione = ?;";
		List<RecensioneBean> result = getJdbcManager().doSelect(query, RECENSIONE_MAPPER, idInserzione);
		return result.isEmpty() ? null : result;
	}
}

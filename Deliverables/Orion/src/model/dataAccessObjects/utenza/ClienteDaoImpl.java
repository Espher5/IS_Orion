package model.dataAccessObjects.utenza;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.beans.utenza.ClienteBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;


public class ClienteDaoImpl extends AbstractDao implements ClienteDao {
	public ClienteDaoImpl() {
		super(new JdbcManagerImpl());
	}

	public static final RowMapper<ClienteBean> CLIENTE_MAPPER = new RowMapper<ClienteBean>() {
		@Override
		public ClienteBean map(ResultSet resultSet) throws SQLException {
			final ClienteBean cliente = new ClienteBean();
			cliente.setIdUtente(resultSet.getLong("ID_utente"));
			cliente.setDataUltimaPrenotazione(resultSet.getDate("ultima_prenotazione"));
			return cliente;
		}
	};
	
	@Override
	public int doSave(ClienteBean cliente) {
		String query = "INSERT INTO Cliente "
					 + "VALUES(?, ?);";
		Object[] parameters = {
			cliente.getIdUtente(),
			cliente.getDataUltimaPrenotazione(),
		};
		return getJdbcManager().doSave(query, parameters);
	}

	@Override
	public int doUpdate(ClienteBean cliente) {
		String query = "UPDATE Cliente "
					 + "SET ultime_prenotazione = ? "
					 + "WHERE ID_utente = ?;";
		Object[] parameters = {
			cliente.getDataUltimaPrenotazione(),
			cliente.getIdUtente()
		};
		return getJdbcManager().doUpdate(query, parameters);
	}

	@Override
	public int doDelete(long idUtente) {
		String query = "DELETE FROM Cliente "
					 + "WHERE ID_cliente = ?";
		return getJdbcManager().doDelete(query, idUtente);
	}

	@Override
	public ClienteBean doRetrieveByKey(long idUtente) {
		String query = "SELECT * "
				 	 + "FROM Cliente "
				 	 + "WHERE ID_utente = ?";
		final List<ClienteBean> result = getJdbcManager().doSelect(query, CLIENTE_MAPPER, idUtente);
		return result.isEmpty() ? null : result.get(0);
	}
}

package model.dataAccessObjects.prenotazioni;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.beans.prenotazioni.MetodoPagamentoBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;


public class MetodoPagamentoDaoImpl extends AbstractDao implements MetodoPagamentoDao {
	public MetodoPagamentoDaoImpl() {
		super(new JdbcManagerImpl());
	}

	public static final RowMapper<MetodoPagamentoBean> METODO_PAGAMENTO_MAPPER = new RowMapper<MetodoPagamentoBean>() {
		@Override
		public MetodoPagamentoBean map(ResultSet resultSet) throws SQLException {
			MetodoPagamentoBean metodoPagamento = new MetodoPagamentoBean();
			metodoPagamento.setNumeroCarta(resultSet.getString("numero_carta"));
			metodoPagamento.setIdUtente(resultSet.getLong("ID_utente"));
			metodoPagamento.setNomeTitolare(resultSet.getString("nome_titolare"));
			metodoPagamento.setCognomeTitolare(resultSet.getString("cognome_titolare"));
			metodoPagamento.setDataScadenza(resultSet.getDate("data_scadenza"));
			metodoPagamento.setPreferito(resultSet.getBoolean("preferito"));
			return metodoPagamento;
		}
	};
	
	@Override
	public int doSave(MetodoPagamentoBean metodoPagamento) {
		String query = "INSERT INTO MetodoPagamento "
					 + "VALUES (?, ?, ?, ?, ?, ?);";
		Object[] parameters = {
			metodoPagamento.getNumeroCarta(),
			metodoPagamento.getIdUtente(),
			metodoPagamento.getNomeTitolare(),
			metodoPagamento.getCognomeTitolare(),
			metodoPagamento.getDataScadenza(),
			metodoPagamento.isPreferito()
		};
		return getJdbcManager().doSave(query, parameters);
	}

	@Override
	public int doUpdate(MetodoPagamentoBean metodoPagamento) {
		String query = "UPDATE MetodoPagamento "
					 + "SET nome_titolare = ?, "
					 + "cognome_titolare = ?, "
					 + "data_scadenza = ?, "
					 + "preferito = ? "
					 + "WHERE numero_carta = ? AND ID_utente = ?;";
		Object[] parameters = {
			metodoPagamento.getNomeTitolare(),
			metodoPagamento.getCognomeTitolare(),
			metodoPagamento.getDataScadenza(),
			metodoPagamento.isPreferito(),
			metodoPagamento.getNumeroCarta(),
			metodoPagamento.getIdUtente()
		};
		return getJdbcManager().doUpdate(query, parameters);
	}

	@Override
	public int doDelete(String numeroCarta) {
		String query = "DELETE FROM MetodoPagamento "
					 + "WHERE numero_carta = ?;";
		return getJdbcManager().doDelete(query, numeroCarta);
	}

	@Override
	public MetodoPagamentoBean doRetrieveByKey(String numeroCarta, long idUtente) {
		String query = "SELECT * "
					 + "FROM MetodoPagamento "
					 + "WHERE numero_carta = ? AND ID_utente = ?;";
		List<MetodoPagamentoBean> result = getJdbcManager().doSelect(query, METODO_PAGAMENTO_MAPPER, numeroCarta, idUtente);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public MetodoPagamentoBean doRetrievePreferito(long idUtente) {
		String query = "SELECT * "
					 + "FROM MetodoPagamento "
					 + "WHERE ID_utente = ? "
					 + "AND preferito = true;";
		List<MetodoPagamentoBean> result = getJdbcManager().doSelect(query, METODO_PAGAMENTO_MAPPER, idUtente);
		return result.isEmpty() ? null : result.get(0);
	}
	
	@Override
	public List<MetodoPagamentoBean> doRetrieveByIdUtente(long idUtente) {
		String query = "SELECT * "
					 + "FROM MetodoPagamento "
					 + "WHERE ID_utente = ?";
		List<MetodoPagamentoBean> result = getJdbcManager().doSelect(query, METODO_PAGAMENTO_MAPPER, idUtente);
		return result.isEmpty() ? null : result;
	}
}

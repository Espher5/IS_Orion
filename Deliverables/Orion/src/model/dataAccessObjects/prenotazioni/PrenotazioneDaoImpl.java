package model.dataAccessObjects.prenotazioni;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.beans.prenotazioni.PrenotazioneBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;

public class PrenotazioneDaoImpl extends AbstractDao implements PrenotazioneDao {
	public PrenotazioneDaoImpl() {
		super(new JdbcManagerImpl());
	}

	public static final RowMapper<PrenotazioneBean> PRENOTAZIONE_MAPPER = new RowMapper<PrenotazioneBean>() {
		@Override
		public PrenotazioneBean map(ResultSet resultSet) throws SQLException {
			PrenotazioneBean prenotazione = new PrenotazioneBean();
			prenotazione.setIdPrenotazione(resultSet.getLong("ID_prenotazione"));
			prenotazione.setIdCliente(resultSet.getLong("ID_cliente"));
			prenotazione.setIdInserzione(resultSet.getLong("ID_inserzione"));
			prenotazione.setMpCliente(resultSet.getString("mp_cliente"));
			prenotazione.setMpProprietario(resultSet.getString("mp_proprietario"));
			prenotazione.setDataPrenotazione(resultSet.getDate("data_prenotazione"));
			prenotazione.setDataCheckIn(resultSet.getDate("data_check_in"));
			prenotazione.setDataCheckOut(resultSet.getDate("data_check_out"));
			prenotazione.setTotale(resultSet.getDouble("totale"));
			prenotazione.setNumeroOspiti(resultSet.getInt("numero_ospiti"));
			prenotazione.setStato(resultSet.getBoolean("stato"));
			return prenotazione;
		}
	};
	
	@Override
	public long doSave(PrenotazioneBean prenotazione) {
		String query = "INSERT INTO Prenotazione (ID_inserzione, ID_cliente, mp_cliente, mp_proprietario, "
					 + "data_prenotazione, data_check_in, data_check_out, totale, numero_ospiti, stato) "
					 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		Object[] parameters = {
			prenotazione.getIdInserzione(),
			prenotazione.getIdCliente(),
			prenotazione.getMpCliente(),
			prenotazione.getMpProprietario(),
			prenotazione.getDataPrenotazione(),
			prenotazione.getDataCheckIn(),
			prenotazione.getDataCheckOut(),
			prenotazione.getTotale(),
			prenotazione.getNumeroOspiti(),
			prenotazione.getStato()
		};
		return getJdbcManager().doSaveWithGeneratedValues(query, parameters);
	}
	
	@Override
	public int doUpdate(PrenotazioneBean prenotazione) {
		String query = "UPDATE Prenotazione "
					 + "SET mp_cliente = ?, "
					 + "mp_proprietario = ?, "
					 + "data_prenotazione = ?, "
					 + "stato = ? "
					 + "WHERE ID_prenotazione = ?;";	
		Object[] parameters = {
				prenotazione.getMpCliente(),
				prenotazione.getMpProprietario(),
				prenotazione.getDataPrenotazione(),
				prenotazione.getStato(),
				prenotazione.getIdPrenotazione()
			};
		return getJdbcManager().doUpdate(query, parameters);
	}

	@Override
	public int doDelete(long idPrenotazione) {
		String query = "DELETE FROM Prenotazione "
					 + "WHERE ID_prenotazione = ?;";
		return getJdbcManager().doDelete(query, idPrenotazione);
	}

	@Override
	public PrenotazioneBean doRetrieveByKey(long idPrenotazione) {
		String query = "SELECT * "
					 + "FROM Prenotazione "
					 + "WHERE ID_prenotazione = ?;";
		List<PrenotazioneBean> result = getJdbcManager().doSelect(query, PRENOTAZIONE_MAPPER, idPrenotazione);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public PrenotazioneBean doRetrieveInSospeso(long idCliente) {
		String query = "SELECT * "
					 + "FROM Prenotazione "
					 + "WHERE ID_cliente = ? "
					 + "AND stato = false;";
		List<PrenotazioneBean> result = getJdbcManager().doSelect(query, PRENOTAZIONE_MAPPER, idCliente);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<PrenotazioneBean> doRetrieveByIdClienteAndIdInserzione(long idCliente, long idInserzione) {
		String query = "SELECT * "
					 + "FROM Prenotazione "
					 + "WHERE ID_cliente = ? "
					 + "AND ID_inserzione = ?;";
		List<PrenotazioneBean> result = getJdbcManager().doSelect(query, PRENOTAZIONE_MAPPER, idCliente, idInserzione);
		return result.isEmpty() ? null : result;
	}
	
	@Override
	public List<PrenotazioneBean> doRetrieveByIdCliente(long idCliente) {
		String query = "SELECT * "
				 	 + "FROM Prenotazione "
				 	 + "WHERE ID_cliente = ?;";
		List<PrenotazioneBean> result = getJdbcManager().doSelect(query, PRENOTAZIONE_MAPPER, idCliente);
		return result.isEmpty() ? null : result;
	}

	@Override
	public List<PrenotazioneBean> doRetrieveByIdInserzione(long idInserzione) {
		String query = "SELECT * "
					 + "FROM Prenotazione "
					 + "WHERE ID_Inserzione = ?";
		List<PrenotazioneBean> result = getJdbcManager().doSelect(query, PRENOTAZIONE_MAPPER, idInserzione);
		return result.isEmpty() ? null : result;
	}
}

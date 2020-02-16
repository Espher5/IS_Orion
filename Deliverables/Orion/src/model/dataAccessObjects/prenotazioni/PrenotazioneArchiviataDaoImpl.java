package model.dataAccessObjects.prenotazioni;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.beans.prenotazioni.PrenotazioneArchiviataBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;

public class PrenotazioneArchiviataDaoImpl extends AbstractDao implements PrenotazioneArchiviataDao {
	public PrenotazioneArchiviataDaoImpl() {
		super(new JdbcManagerImpl());
	}
	
	public static final RowMapper<PrenotazioneArchiviataBean> PRENOTAZIONE_ARCHIVIATA_MAPPER = new RowMapper<PrenotazioneArchiviataBean>() {
		@Override
		public PrenotazioneArchiviataBean map(ResultSet resultSet) throws SQLException {
			PrenotazioneArchiviataBean prenotazioneArchiviata = new PrenotazioneArchiviataBean();
			prenotazioneArchiviata.setIDPrenotazione(resultSet.getLong("ID_prenotazione"));
			prenotazioneArchiviata.setStato(resultSet.getString("stato"));
			prenotazioneArchiviata.setRegione(resultSet.getString("regione"));
			prenotazioneArchiviata.setCittà(resultSet.getString("città"));
			prenotazioneArchiviata.setCap(resultSet.getString("cap"));
			prenotazioneArchiviata.setIndirizzo(resultSet.getString("indirizzo"));
			prenotazioneArchiviata.setPrezzoGiornaliero(resultSet.getDouble("prezzo_giornaliero"));
			return prenotazioneArchiviata;
		}
	};

	@Override
	public int doSave(PrenotazioneArchiviataBean prenotazioneArchivivata) {
		String query = "INSERT INTO PrenotazioneArchiviata "
					 + "VALUES (?, ?, ?, ?, ?, ?, ?);";
		Object[] parameters = {
			prenotazioneArchivivata.getIdPrenotazione(),
			prenotazioneArchivivata.getStato(),
			prenotazioneArchivivata.getRegione(),
			prenotazioneArchivivata.getCittà(),
			prenotazioneArchivivata.getCap(),
			prenotazioneArchivivata.getIndirizzo(),
			prenotazioneArchivivata.getPrezzoGiornaliero()
		};
		return getJdbcManager().doSave(query, parameters);
	}

	@Override
	public int doDelete(long idPrenotazione) {
		String query = "DELETE FROM PrenotazioneArchivivata "
					 + "WHERE ID_prenotazione = ?;";
		return getJdbcManager().doDelete(query, idPrenotazione);
	}

	@Override
	public PrenotazioneArchiviataBean doRetrieveByKey(long idPrenotazione) {
		String query = "SELECT * "
					 + "FROM PrenotazioneArchiviata "
					 + "WHERE ID_prenotazione = ?;";
		List<PrenotazioneArchiviataBean> result = getJdbcManager().doSelect(query, PRENOTAZIONE_ARCHIVIATA_MAPPER, idPrenotazione);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<PrenotazioneArchiviataBean> doRetrieveByIdUtente(long idUtente) {
		String query = "SELECT * "
					 + "FROM PrenotazioneArchiviata AS PA JOIN Prenotazione AS P "
					 + "ON PA.ID_prenotazione = P.ID_prenotazione "
					 + "WHERE P.ID_cliente = ?;";
		List<PrenotazioneArchiviataBean> result = getJdbcManager().doSelect(query, PRENOTAZIONE_ARCHIVIATA_MAPPER, idUtente);
		return result.isEmpty() ? null : result;
	}

	@Override
	public List<PrenotazioneArchiviataBean> doRetrieveByIdInserzione(long idInserzione) {
		String query = "SELECT * "
				 + "FROM PrenotazioneArchiviata AS PA JOIN Prenotazione AS P "
				 + "ON PA.ID_prenotazione = P.ID_prenotazione "
				 + "WHERE P.ID_inserzione = ?;";
		List<PrenotazioneArchiviataBean> result = getJdbcManager().doSelect(query, PRENOTAZIONE_ARCHIVIATA_MAPPER, idInserzione);
		return result.isEmpty() ? null : result;
	}
}

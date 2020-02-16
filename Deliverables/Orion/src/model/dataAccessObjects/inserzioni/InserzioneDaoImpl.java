package model.dataAccessObjects.inserzioni;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.beans.inserzioni.InserzioneBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;


public class InserzioneDaoImpl extends AbstractDao implements InserzioneDao {
	public InserzioneDaoImpl( ) {
		super(new JdbcManagerImpl());
	}
	
	public static final RowMapper<InserzioneBean> INSERZIONE_MAPPER = new RowMapper<InserzioneBean>() {
		@Override
		public InserzioneBean map(final ResultSet resultSet) throws SQLException {
			final InserzioneBean inserzione = new InserzioneBean();
			inserzione.setIdInserzione(resultSet.getLong("ID_inserzione"));
			inserzione.setIdProprietario(resultSet.getLong("ID_proprietario"));
			inserzione.setStato(resultSet.getString("stato"));
			inserzione.setRegione(resultSet.getString("regione"));
			inserzione.setCittà(resultSet.getString("città"));
			inserzione.setCap(resultSet.getString("cap"));
			inserzione.setStrada(resultSet.getString("strada"));
			inserzione.setNumeroCivico(resultSet.getInt("numero_civico"));
			inserzione.setPrezzoGiornaliero(resultSet.getDouble("prezzo_giornaliero"));
			inserzione.setMaxNumeroOspiti(resultSet.getInt("max_numero_ospiti"));
			inserzione.setMetratura(resultSet.getDouble("metratura"));
			inserzione.setDescrizione(resultSet.getString("descrizione"));
			inserzione.setDataInserimento(resultSet.getDate("data_inserimento"));
			inserzione.setVisibilità(resultSet.getBoolean("visibilità"));
			return inserzione;
		}
	};
	
	@Override
	public long doSave(InserzioneBean inserzione) {
		String query = "INSERT INTO Inserzione (ID_proprietario, stato, regione, città, cap, "
					 + "strada, numero_civico, prezzo_giornaliero, max_numero_ospiti, "
					 + "metratura, descrizione, data_inserimento, visibilità) "
					 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		Object[] parameters = {
			inserzione.getIdProprietario(),
			inserzione.getStato(),
			inserzione.getRegione(),
			inserzione.getCittà(),
			inserzione.getCap(),
			inserzione.getStrada(),
			inserzione.getNumeroCivico(),
			inserzione.getPrezzoGiornaliero(),
			inserzione.getMaxNumeroOspiti(),
			inserzione.getMetratura(),
			inserzione.getDescrizione(),
			inserzione.getDataInserimento(),
			inserzione.getVisibilità()
		};
		return getJdbcManager().doSaveWithGeneratedValues(query, parameters);
	}

	@Override
	public int doUpdate(InserzioneBean inserzione) {
		String query = "UPDATE Inserzione "
					 + "SET stato = ?, "
					 + "regione = ?, "
					 + "città = ?, "
					 + "cap = ?, "
					 + "strada = ?, "
					 + "numero_civico = ?, "
					 + "prezzo_giornaliero = ?, "
					 + "max_numero_ospiti = ?, "
					 + "metratura = ?, "
					 + "descrizione = ?, "
					 + "visibilità = ? "
					 + "WHERE ID_inserzione = ?;";
		Object[] parameters = {
				inserzione.getStato(),
				inserzione.getRegione(),
				inserzione.getCittà(),
				inserzione.getCap(),
				inserzione.getStrada(),
				inserzione.getNumeroCivico(),
				inserzione.getPrezzoGiornaliero(),
				inserzione.getMaxNumeroOspiti(),
				inserzione.getMetratura(),
				inserzione.getDescrizione(),
				inserzione.getVisibilità(),
				inserzione.getIdInserzione()
			};
		return getJdbcManager().doUpdate(query, parameters);
	}

	@Override
	public int doDelete(long idInserzione) {
		String query = "DELETE FROM Inserzione "
					 + "WHERE ID_inserzione = ?;";
		return getJdbcManager().doDelete(query, idInserzione);
	}

	@Override
	public InserzioneBean doRetrieveByKey(long idInserzione) {
		String query = "SELECT * "
					 + "FROM Inserzione "
					 + "WHERE ID_inserzione = ?;";
		List<InserzioneBean> result = getJdbcManager().doSelect(query, INSERZIONE_MAPPER, idInserzione);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<InserzioneBean> doRetrieveByVisibility(boolean visibilità) {
		String query = "SELECT * "
					 + "FROM Inserzione "
					 + "WHERE visibilità = ?;";
		List<InserzioneBean> result = getJdbcManager().doSelect(query, INSERZIONE_MAPPER, visibilità);
		return result.isEmpty() ? null : result;
	}
	
	@Override
	public List<InserzioneBean> doRetrieveAll(int limit, int offset) {
		String query = "SELECT * "
					 + "FROM Inserzione "
					 + "WHERE visibilità = true "
					 + "LIMIT ? OFFSET ?;";
		List<InserzioneBean> result = getJdbcManager().doSelect(query, INSERZIONE_MAPPER, limit, offset);
		return result.isEmpty() ? null : result;
	}

	@Override
	public List<InserzioneBean> doSearch(List<Object> parameters, String[] stili, int offset) {
		String query = "SELECT * "
					 + "FROM Inserzione AS I "
					 + "JOIN IntervalloDisponibilità AS ID ON I.ID_inserzione = ID.ID_inserzione " 
					 + "WHERE I.visibilità = true "
					 + "AND I.stato LIKE ? " 
					 + "AND I.città LIKE ? "
					 + "AND I.prezzo_giornaliero BETWEEN ? AND ? "
					 + "AND I.max_numero_ospiti >= ? ";
			
		/*
		 * Aggiugne la porzione di query relativa alle date di disponibilità
		 */
		if(parameters.size() > 5) {	
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dataCheckIn = format.format(parameters.get(5)).replaceAll("[\\s\\-()]", "");
			String dataCheckOut = format.format(parameters.get(6)).replaceAll("[\\s\\-()]", "");
			parameters.remove(5);
			parameters.remove(5);
			query += "AND ID.data_inizio <= " + dataCheckIn + " "
				   + "AND ID.data_fine >= " + dataCheckOut + " ";
			
		}
		
		/*
		 * Aggiunge la porzione di query relativa agli stili
		 */
		List<Object> newParameters = new ArrayList<Object>(parameters);		
		if(stili != null && stili.length > 0) {
			query += "AND EXISTS (SELECT * "  
					  		   + "FROM AppartenenzaStile AS A " 
					  		   + "WHERE A.ID_inserzione = I.ID_inserzione "
					  		   + "AND A.nome_stile IN ( ";

			for(int i = 0; i < stili.length; i++) {
				newParameters.add(stili[i]);
				query += (i == stili.length - 1) ? "?" : "?, ";
			}
			query += ")) ";

		}	
		query += "GROUP BY I.ID_inserzione "
			   + "LIMIT 10 OFFSET ?;";
		newParameters.add(offset);
		List<InserzioneBean> result = getJdbcManager().doSelect(query, INSERZIONE_MAPPER, newParameters.toArray());
		return result.isEmpty() ? null : result;
	}

	@Override
	public List<InserzioneBean> doRetrieveByIdUtente(long idUtente) {
		String query = "SELECT * "
					 + "FROM Inserzione "
					 + "WHERE ID_proprietario = ?;";
		List<InserzioneBean> result = getJdbcManager().doSelect(query, INSERZIONE_MAPPER, idUtente);
		return result.isEmpty() ? null : result;
	}
	
	
}

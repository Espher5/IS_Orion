package model.dataAccessObjects.inserzioni;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.beans.inserzioni.IntervalloDisponibilit‡Bean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;


public class IntervalloDisponibilit‡DaoImpl extends AbstractDao implements IntervalloDisponibilit‡Dao {
	public IntervalloDisponibilit‡DaoImpl() {
		super(new JdbcManagerImpl());
	}
	
	public static final RowMapper<IntervalloDisponibilit‡Bean> INTERVALLO_DISPONIBILIT¿_MAPPER = new RowMapper<IntervalloDisponibilit‡Bean>() {
		@Override
		public IntervalloDisponibilit‡Bean map(ResultSet resultSet) throws SQLException {
			IntervalloDisponibilit‡Bean intervalloDisponibilit‡ = new IntervalloDisponibilit‡Bean();
			intervalloDisponibilit‡.setIdInserzione(resultSet.getLong("ID_inserzione"));
			intervalloDisponibilit‡.setDataInizio(resultSet.getDate("data_inizio"));
			intervalloDisponibilit‡.setDataFine(resultSet.getDate("data_fine"));
			return intervalloDisponibilit‡;
		}
	};

	@Override
	public int doSave(IntervalloDisponibilit‡Bean intervalloDisponibilit‡) {
		String query = "INSERT INTO IntervalloDisponibilit‡ "
					 + "VALUES (?, ?, ?)";
		Object[] parameters = {
			intervalloDisponibilit‡.getIdInserzione(),
			intervalloDisponibilit‡.getDataInizio(),
			intervalloDisponibilit‡.getDataFine()
		};
		return getJdbcManager().doSave(query, parameters);
	}
	
	@Override
	public int doDelete(long idInserzione, Date dataInizio, Date dataFine) {
		String query = "DELETE FROM IntervalloDisponibilit‡ "
					 + "WHERE ID_inserzione = ? AND data_inizio = ? AND data_fine = ?;";
		return getJdbcManager().doDelete(query, idInserzione, dataInizio, dataFine);
	}

	@Override
	public IntervalloDisponibilit‡Bean doRetrieveByKey(long idInserzione, Date dataInizio, Date dataFine) {
		String query = "SELECT * FROM IntervalloDisponibilit‡ "
					 + "WHERE ID_inserzione = ? AND data_inzio = ? AND data_fine = ?;";
		List<IntervalloDisponibilit‡Bean> result = getJdbcManager().doSelect(query, INTERVALLO_DISPONIBILIT¿_MAPPER, idInserzione, dataInizio, dataFine);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<IntervalloDisponibilit‡Bean> doRetrieveByIdInserzione(long idInserzione) {
		String query = "SELECT * "
					 + "FROM IntervalloDisponibilit‡ "
					 + "WHERE ID_inserzione = ?;";
		List<IntervalloDisponibilit‡Bean> result = getJdbcManager().doSelect(query, INTERVALLO_DISPONIBILIT¿_MAPPER, idInserzione);
		return result.isEmpty() ? null : result;
	}

	@Override
	public IntervalloDisponibilit‡Bean doRetrieveByDate(long idInserzione, Date dataInizio, Date dataFine) {
		String query = "SELECT * "
					 + "FROM IntervalloDisponibilit‡ AS I "
					 + "WHERE EXISTS (SELECT * "
					 			   + "FROM IntervalloDisponibilit‡ "
					 			   + "WHERE ID_inserzione = ? "
					 			   + "AND ID_inserzione = I.ID_inserzione "
					 			   + "AND data_inizio BETWEEN I.data_inizio AND ? "
					 			   + "AND data_fine BETWEEN ? AND I.data_fine);";
		List<IntervalloDisponibilit‡Bean> result = getJdbcManager().doSelect(query, INTERVALLO_DISPONIBILIT¿_MAPPER, idInserzione, dataInizio, dataFine);
		return result.isEmpty() ? null : result.get(0);
	}
}

package model.dataAccessObjects.inserzioni;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.beans.inserzioni.IntervalloDisponibilitÓBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;


public class IntervalloDisponibilitÓDaoImpl extends AbstractDao implements IntervalloDisponibilitÓDao {
	public IntervalloDisponibilitÓDaoImpl() {
		super(new JdbcManagerImpl());
	}
	
	public static final RowMapper<IntervalloDisponibilitÓBean> INTERVALLO_DISPONIBILIT└_MAPPER = new RowMapper<IntervalloDisponibilitÓBean>() {
		@Override
		public IntervalloDisponibilitÓBean map(ResultSet resultSet) throws SQLException {
			IntervalloDisponibilitÓBean intervalloDisponibilitÓ = new IntervalloDisponibilitÓBean();
			intervalloDisponibilitÓ.setIdInserzione(resultSet.getLong("ID_inserzione"));
			intervalloDisponibilitÓ.setDataInizio(resultSet.getDate("data_inizio"));
			intervalloDisponibilitÓ.setDataFine(resultSet.getDate("data_fine"));
			return intervalloDisponibilitÓ;
		}
	};

	@Override
	public int doSave(IntervalloDisponibilitÓBean intervalloDisponibilitÓ) {
		String query = "INSERT INTO IntervalloDisponibilitÓ "
					 + "VALUES (?, ?, ?)";
		Object[] parameters = {
			intervalloDisponibilitÓ.getIdInserzione(),
			intervalloDisponibilitÓ.getDataInizio(),
			intervalloDisponibilitÓ.getDataFine()
		};
		return getJdbcManager().doSave(query, parameters);
	}
	
	@Override
	public int doDelete(long idInserzione, Date dataInizio, Date dataFine) {
		String query = "DELETE FROM IntervalloDisponibilitÓ "
					 + "WHERE ID_inserzione = ? AND data_inizio = ? AND data_fine = ?;";
		return getJdbcManager().doDelete(query, idInserzione, dataInizio, dataFine);
	}

	@Override
	public IntervalloDisponibilitÓBean doRetrieveByKey(long idInserzione, Date dataInizio, Date dataFine) {
		String query = "SELECT * FROM IntervalloDisponibilitÓ "
					 + "WHERE ID_inserzione = ? AND data_inzio = ? AND data_fine = ?;";
		List<IntervalloDisponibilitÓBean> result = getJdbcManager().doSelect(query, INTERVALLO_DISPONIBILIT└_MAPPER, idInserzione, dataInizio, dataFine);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<IntervalloDisponibilitÓBean> doRetrieveByIdInserzione(long idInserzione) {
		String query = "SELECT * "
					 + "FROM IntervalloDisponibilitÓ "
					 + "WHERE ID_inserzione = ?;";
		List<IntervalloDisponibilitÓBean> result = getJdbcManager().doSelect(query, INTERVALLO_DISPONIBILIT└_MAPPER, idInserzione);
		return result.isEmpty() ? null : result;
	}

	@Override
	public IntervalloDisponibilitÓBean doRetrieveByDate(long idInserzione, Date dataInizio, Date dataFine) {
		String query = "SELECT * "
					 + "FROM IntervalloDisponibilitÓ AS I "
					 + "WHERE EXISTS (SELECT * "
					 			   + "FROM IntervalloDisponibilitÓ "
					 			   + "WHERE ID_inserzione = ? "
					 			   + "AND ID_inserzione = I.ID_inserzione "
					 			   + "AND data_inizio BETWEEN I.data_inizio AND ? "
					 			   + "AND data_fine BETWEEN ? AND I.data_fine);";
		List<IntervalloDisponibilitÓBean> result = getJdbcManager().doSelect(query, INTERVALLO_DISPONIBILIT└_MAPPER, idInserzione, dataInizio, dataFine);
		return result.isEmpty() ? null : result.get(0);
	}
}

package model.dataAccessObjects.inserzioni;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.beans.inserzioni.CommentoBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;

public class CommentoDaoImpl extends AbstractDao implements CommentoDao {
	public CommentoDaoImpl() {
		super(new JdbcManagerImpl());
	}
	
	public static final RowMapper<CommentoBean> COMMENTO_MAPPER = new RowMapper<CommentoBean>() {
		@Override
		public CommentoBean map(ResultSet resultSet) throws SQLException {
			CommentoBean commento = new CommentoBean();
			commento.setIdRecensione(resultSet.getLong("ID_recensione"));
			commento.setContenuto(resultSet.getString("contenuto"));
			commento.setDataPubblicazione(resultSet.getDate("data_pubblicazione"));
			return commento;
		}
	};

	@Override
	public int doSave(CommentoBean commento) {
		String query = "INSERT INTO Commento "
					 + "VALUES (?, ?, ?);";
		Object[] parameters = {
			commento.getIdRecensione(),
			commento.getContenuto(),
			commento.getDataPubblicazione()
		};
		return getJdbcManager().doSave(query, parameters);
	}

	@Override
	public int doUpdate(CommentoBean commento) {
		String query = "UPDATE Commento "
					 + "SET contenuto = ? "
					 + "WHERE ID_recensione = ?;";
		Object[] parameters = {
			commento.getContenuto(),
			commento.getIdRecensione()
		};
		return getJdbcManager().doUpdate(query, parameters);
	}

	@Override
	public int doDelete(long idRecensione) {
		String query = "DELETE FROM Commento "
					 + "WHERE ID_recensione = ?;";
		return getJdbcManager().doDelete(query, idRecensione);
	}

	@Override
	public CommentoBean doRetrieveByKey(long idRecensione) {
		String query = "SELECT * "
					 + "FROM Commento "
					 + "WHERE ID_recensione = ?;";
		List<CommentoBean> result = getJdbcManager().doSelect(query, COMMENTO_MAPPER, idRecensione);
		return result.isEmpty() ? null : result.get(0);
	}
}

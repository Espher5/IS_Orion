package model.dataAccessObjects.inserzioni;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.beans.inserzioni.StileBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;

public class StileDaoImpl extends AbstractDao implements StileDao {
	public StileDaoImpl() {
		super(new JdbcManagerImpl());
	}

	public static final RowMapper<StileBean> STILE_MAPPER = new RowMapper<StileBean>() {
		@Override
		public StileBean map(ResultSet resultSet) throws SQLException {
			StileBean stile = new StileBean();
			stile.setNomeStile(resultSet.getString("nome_stile"));
			stile.setDescrizione(resultSet.getString("descrizione"));
			return stile;
		}
	};
		
	@Override
	public int doSave(StileBean stile) {
		String query = "INSERT INTO Stile "
					 + "VALUES (?, ?);";
		Object[] parameters = {
				stile.getNomeStile(),
				stile.getDescrizione()
		};
		return getJdbcManager().doSave(query, parameters);
	}

	@Override
	public int doUpdate(StileBean stile) {
		String query = "UPDATE Stile "
				 	 + "SET descrizione = ? "
				 	 + "WHERE nome_stile = ?;";
		Object[] parameters = {
			stile.getDescrizione(),
			stile.getNomeStile()
		};
		return getJdbcManager().doUpdate(query, parameters);
	}

	@Override
	public int doDelete(String nomeStile) {
		String query = "DELETE FROM Stile "
					 + "WHERE nome_stile = ?;";
		return getJdbcManager().doDelete(query, nomeStile);
	}

	@Override
	public StileBean doRetrieveByKey(String nomeStile) {
		String query = "SELECT * "
					 + "FROM Stile "
					 + "WHERE nome_stile = ?;";
		List<StileBean> result = getJdbcManager().doSelect(query, STILE_MAPPER, nomeStile);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<StileBean> doRetrieveAll() {
		String query = "SELECT * "
					 + "FROM Stile;";
		List<StileBean> result = getJdbcManager().doSelect(query, STILE_MAPPER);
		return result.isEmpty() ? null : result;
	}
}

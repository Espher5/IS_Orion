package model.dataAccessObjects.inserzioni;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.beans.inserzioni.AppartenenzaStileBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;

public class AppartenenzaStileDaoImpl extends AbstractDao implements AppartenenzaStileDao {
	public AppartenenzaStileDaoImpl() {
		super(new JdbcManagerImpl());
	}
	
	public static final RowMapper<AppartenenzaStileBean> APPARTENENZA_STILE_MAPPER = new RowMapper<AppartenenzaStileBean>() {
		@Override
		public AppartenenzaStileBean map(ResultSet resultSet) throws SQLException {
			AppartenenzaStileBean appartenenzaStile = new AppartenenzaStileBean();
			appartenenzaStile.setIdInserzione(resultSet.getLong("ID_inserzione"));
			appartenenzaStile.setNomeStile(resultSet.getString("nome_stile"));
			return appartenenzaStile;
		}
	};

	@Override
	public int doSave(AppartenenzaStileBean appartenenzaStile) {
		String query = "INSERT INTO AppartenenzaStile "
					 + "VALUES (?, ?);";
		Object[] parameters = {
			appartenenzaStile.getIdInserzione(),
			appartenenzaStile.getNomeStile()
		};
		return getJdbcManager().doSave(query, parameters);
	}

	@Override
	public int doDelete(long idInserzione, String nomeStile) {
		String query = "DELETE FROM AppartenenzaStile "
					 + "WHERE ID_inserzione = ? AND nome_stile = ?;";	
		return getJdbcManager().doDelete(query, idInserzione, nomeStile);
	}

	@Override
	public AppartenenzaStileBean doRetrieveByKey(long idInserzione, String nomeStile) {
		String query = "SELECT * "
					 + "FROM AppartenenzaStile "
					 + "WHERE ID_inserzione = ? AND nome_stile = ?;";
		List<AppartenenzaStileBean> result = getJdbcManager().doSelect(query, APPARTENENZA_STILE_MAPPER, idInserzione, nomeStile);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<AppartenenzaStileBean> doRetrieveByIdInserzione(long idInserzione) {
		String query = "SELECT * "
					 + "FROM AppartenenzaStile "
					 + "WHERE ID_inserzione = ?;";
		List<AppartenenzaStileBean> result = getJdbcManager().doSelect(query, APPARTENENZA_STILE_MAPPER, idInserzione);
		return result.isEmpty() ? null : result;
	}
}

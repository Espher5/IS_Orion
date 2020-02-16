package model.dataAccessObjects.inserzioni;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.beans.inserzioni.ImmagineBean;
import model.dataAccessObjects.AbstractDao;
import model.dataAccessObjects.JdbcManagerImpl;
import model.dataAccessObjects.RowMapper;


public class ImmagineDaoImpl extends AbstractDao implements ImmagineDao {
	public ImmagineDaoImpl() {
		super(new JdbcManagerImpl());
	}

	public static final RowMapper<ImmagineBean> IMMAGINE_MAPPER = new RowMapper<ImmagineBean>() {
		@Override
		public ImmagineBean map(final ResultSet resultSet) throws SQLException {
			ImmagineBean immagine = new ImmagineBean();
			immagine.setPathname(resultSet.getString("pathname"));
			immagine.setIdInserzione(resultSet.getLong("ID_inserzione"));
			return immagine;
		}
	};
	
	@Override
	public int doSave(ImmagineBean immagine) {
		String query = "INSERT INTO Immagine "
					 + "VALUES (?, ?);";
		Object[] parameters = {
			immagine.getPathname(),
			immagine.getIdInserzione()
		};
		return getJdbcManager().doSave(query, parameters);
	}

	@Override
	public int doDelete(String pathname) {
		String query = "DELETE FROM Immagine "
					 + "WHERE pathname = ?;";
		return getJdbcManager().doSave(query, pathname);
	}

	@Override
	public ImmagineBean doRetrieveByKey(String pathname) {
		String query = "SELECT * "
					 + "FROM Immagine "
					 + "WHERE pathname = ?;";
		List<ImmagineBean> result = getJdbcManager().doSelect(query, IMMAGINE_MAPPER, pathname);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<ImmagineBean> doRetrieveByIdInserzione(long idInserzione) {
		String query = "SELECT * "
					 + "FROM Immagine "
					 + "WHERE ID_inserzione = ?";
		List<ImmagineBean> result = getJdbcManager().doSelect(query, IMMAGINE_MAPPER, idInserzione);
		return result.isEmpty() ? null : result;
	}

}

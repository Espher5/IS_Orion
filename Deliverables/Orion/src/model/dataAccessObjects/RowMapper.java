package model.dataAccessObjects;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<E> {
	E map(ResultSet resultSet) throws SQLException;
}

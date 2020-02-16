package model.dataAccessObjects;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class JdbcManagerImpl implements JdbcManager {
	protected final Connection getConnection() {
		Connection connection = null;
		try {
			connection = DBConnectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	
	public void releaseResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		if(connection != null) {
			try {
				DBConnectionPool.releaseConnection(connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void setParameters(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
		for(int i = 0, length = parameters.length; i < length; i++) {
			final Object parameter = parameters[i];
			final int parameterIndex = i + 1;
			if(parameter == null) {
				preparedStatement.setObject(parameterIndex, null);
			}
			else if(parameter instanceof BigDecimal) {
				preparedStatement.setBigDecimal(parameterIndex, (BigDecimal) parameter);
			}
			else if(parameter instanceof Boolean) {
				preparedStatement.setBoolean(parameterIndex, (Boolean) parameter);
			}
			else if(parameter instanceof Byte) {
				preparedStatement.setByte(parameterIndex, (Byte) parameter);
			}
			else if(parameter instanceof Calendar) {
				preparedStatement.setDate(parameterIndex, new java.sql.Date(((Calendar) parameter).getTimeInMillis()));
			}
			else if(parameter instanceof Character) {
				preparedStatement.setString(parameterIndex, String.valueOf(parameter));
			}
			else if(parameter instanceof Date) {
				preparedStatement.setDate(parameterIndex, new java.sql.Date(((Date) parameter).getTime()));
			}
			else if(parameter instanceof Double) {
				preparedStatement.setDouble(parameterIndex, (Double) parameter);
			}
			else if(parameter instanceof Float) {
				preparedStatement.setFloat(parameterIndex, (Float) parameter);
			}
			else if(parameter instanceof Integer) {
				preparedStatement.setInt(parameterIndex, (Integer) parameter);
			}
			else if(parameter instanceof Long) {
				preparedStatement.setLong(parameterIndex, (Long) parameter);
			}
			else if(parameter instanceof Short) {
				preparedStatement.setShort(parameterIndex, (Short) parameter);
			}
			else if(parameter instanceof String) {
				preparedStatement.setString(parameterIndex, (String) parameter);
			}
			else {
				throw new IllegalArgumentException(String.format(
						"Il tipo del parametro è sconosciuto. [parametro: %s, indice: %s]", 
						parameter, parameterIndex));
			}
		}
	}
	
	
	@Override
	public int doSave(final String query, final Object... parameters) throws DataAccessException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;	
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			setParameters(preparedStatement, (Object[]) parameters);
			return preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new DataAccessOperationErrorException(e);
		} finally {
			releaseResources(connection, preparedStatement, null);
		}
	}
	
	
	@Override
	public long doSaveWithGeneratedValues(final String query, final Object... parameters) throws DataAccessException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;	
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			setParameters(preparedStatement, (Object[]) parameters);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) return resultSet.getLong(1);
			else return -1;
		} catch(SQLException e) {
			throw new DataAccessOperationErrorException(e);
		} finally {
			releaseResources(connection, preparedStatement, null);
		}
	}


	@Override
	public int doUpdate(final String query, final Object... parameters) throws DataAccessException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;	
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			setParameters(preparedStatement, (Object[]) parameters);
			return preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new DataAccessOperationErrorException(e);
		} finally {
			releaseResources(connection, preparedStatement, null);
		}
	}


	@Override
	public int doDelete(final String query, final Object... parameters) throws DataAccessException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;	
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			setParameters(preparedStatement, (Object[]) parameters);
			return preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new DataAccessOperationErrorException(e);
		} finally {
			releaseResources(connection, preparedStatement, null);
		}
	}
	
	
	@Override
	public <E> List<E> doSelect(final String query, final RowMapper<E> rowMapper, final Object... parameters) throws DataAccessException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		final List<E> result = new ArrayList<E>();		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			setParameters(preparedStatement,(Object[]) parameters);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				result.add(rowMapper.map(resultSet));
			}
		} catch(final SQLException e) {
			throw new DataAccessOperationErrorException(e);
		} finally {
			releaseResources(connection, preparedStatement, resultSet);
		}
		return result;
	}
}

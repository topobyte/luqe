// Copyright 2013 Sebastian Kuerten
//
// This file is part of luqe.
//
// luqe is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// luqe is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with luqe. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.luqe.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.luqe.iface.IPreparedStatement;
import de.topobyte.luqe.iface.IResultSet;
import de.topobyte.luqe.iface.QueryException;

public class JdbcPreparedStatement implements IPreparedStatement
{

	private static Logger logger = LoggerFactory
			.getLogger(JdbcPreparedStatement.class);

	private final PreparedStatement statement;

	public JdbcPreparedStatement(PreparedStatement statement)
	{
		this.statement = statement;
	}

	@Override
	public IResultSet executeQuery() throws QueryException
	{
		logger.debug("executeQuery");
		try {
			boolean hasResultSet = statement.execute();
			if (hasResultSet) {
				ResultSet resultSet = statement.getResultSet();
				return new JdbcResultSet(resultSet);
			}
			// Well the behaviour concerning generated keys is non-standard but
			// works like this
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys != null) {
				return new JdbcResultSet(generatedKeys);
			}
			return null;
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public void execute() throws QueryException
	{
		executeQuery();
	}

	@Override
	public void setInt(int position, int value) throws QueryException
	{
		logger.debug(
				String.format("setInt: pos: %d, value: %d", position, value));
		try {
			statement.setInt(position, value);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public void setLong(int position, long value) throws QueryException
	{
		logger.debug(
				String.format("setInt: pos: %d, value: %d", position, value));
		try {
			statement.setLong(position, value);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public void setDouble(int position, double value) throws QueryException
	{
		logger.debug(String.format("setDouble: pos: %d, value: %f", position,
				value));
		try {
			statement.setDouble(position, value);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public void setString(int position, String value) throws QueryException
	{
		logger.debug(String.format("setString: pos: %d, value: %s", position,
				value));
		try {
			statement.setString(position, value);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public void setBlob(int position, byte[] value) throws QueryException
	{
		logger.debug(String.format("setBlob: pos: %d, value: %d bytes",
				position, value.length));
		try {
			statement.setBytes(position, value);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public void setBoolean(int position, boolean value) throws QueryException
	{
		logger.debug(String.format("setBoolean: pos: %d, value: %b", position,
				value));
		try {
			statement.setBoolean(position, value);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public void setArguments(String[] arguments) throws QueryException
	{
		logger.debug(String.format("setArguments: %d", arguments.length));
		for (int i = 0; i < arguments.length; i++) {
			try {
				statement.setString(i + 1, arguments[i]);
			} catch (SQLException e) {
				throw new QueryException(e);
			}
		}
	}

	@Override
	public void close() throws QueryException
	{
		try {
			statement.close();
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

}

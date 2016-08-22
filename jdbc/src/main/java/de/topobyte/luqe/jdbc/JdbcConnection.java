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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.IPreparedStatement;
import de.topobyte.luqe.iface.QueryException;

public class JdbcConnection implements IConnection
{

	private static Logger logger = LoggerFactory
			.getLogger(JdbcConnection.class);

	private final Connection connection;

	public JdbcConnection(Connection connection) throws SQLException
	{
		this.connection = connection;
	}

	@Override
	public IPreparedStatement prepareStatement(String sql)
			throws QueryException
	{
		logger.debug("prepareStatment: " + sql);
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			return new JdbcPreparedStatement(statement);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public void execute(String sql) throws QueryException
	{
		IPreparedStatement statement = prepareStatement(sql);
		statement.executeQuery();
	}

}

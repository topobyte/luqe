// Copyright 2019 Sebastian Kuerten
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

package de.topobyte.luqe.jdbc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.jdbc.JdbcConnection;

public class Database
{

	final static Logger logger = LoggerFactory.getLogger(Database.class);

	private Connection jdbcConnection = null;
	private IConnection connection = null;

	public Database(String url)
	{
		this(url, false);
	}

	public Database(String url, boolean autoCommit)
	{
		logger.debug("configuring database connection");
		try {
			jdbcConnection = DriverManager.getConnection(url);
			jdbcConnection.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			logger.error("unable to create jdbc connection", e);
			return;
		}
		try {
			connection = new JdbcConnection(jdbcConnection);
		} catch (SQLException e) {
			logger.error("unable to create jdbc connection", e);
		}
	}

	public void closeConnection(boolean commit)
	{
		logger.debug("closing database connection");
		try {
			if (jdbcConnection != null) {
				if (commit) {
					jdbcConnection.commit();
				}
				jdbcConnection.close();
			}
		} catch (SQLException e) {
			logger.error("error while closing connection", e);
		}
	}

	public Connection getJdbcConnection()
	{
		return jdbcConnection;
	}

	public IConnection getConnection()
	{
		return connection;
	}

}

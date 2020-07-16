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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import de.topobyte.luqe.iface.IResultSet;
import de.topobyte.luqe.iface.QueryException;

public class JdbcResultSet implements IResultSet
{

	private final ResultSet resultSet;

	public JdbcResultSet(ResultSet resultSet)
	{
		this.resultSet = resultSet;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			int count = metaData.getColumnCount();
			for (int i = 1; i <= count; i++) {
				String columnName = metaData.getColumnName(i);
				int type = metaData.getColumnType(i);
				if (type == Types.INTEGER) {
					int v = resultSet.getInt(i);
					builder.append(String.format("%s: %d, ", columnName, v));
				} else if (type == Types.VARCHAR) {
					String v = resultSet.getString(i);
					builder.append(String.format("%s: %s, ", columnName, v));
				} else {
					builder.append(String.format("%s: ?, ", columnName));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	@Override
	public boolean next() throws QueryException
	{
		try {
			return resultSet.next();
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public int getInt(int position) throws QueryException
	{
		try {
			return resultSet.getInt(position);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public long getLong(int position) throws QueryException
	{
		try {
			return resultSet.getLong(position);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public double getDouble(int position) throws QueryException
	{
		try {
			return resultSet.getDouble(position);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public String getString(int position) throws QueryException
	{
		try {
			return resultSet.getString(position);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public boolean getBoolean(int position) throws QueryException
	{
		try {
			return resultSet.getBoolean(position);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public byte[] getBlob(int position) throws QueryException
	{
		try {
			return resultSet.getBytes(position);
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public void close() throws QueryException
	{
		try {
			resultSet.close();
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

}

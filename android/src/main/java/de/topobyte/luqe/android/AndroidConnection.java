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

package de.topobyte.luqe.android;

import android.database.sqlite.SQLiteDatabase;
import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.IPreparedStatement;
import de.topobyte.luqe.iface.QueryException;

public class AndroidConnection implements IConnection
{

	private final SQLiteDatabase database;

	public AndroidConnection(SQLiteDatabase database)
	{
		this.database = database;
	}

	@Override
	public IPreparedStatement prepareStatement(String sql)
	{
		/*
		 * Depending on the query type we use either the fake prepared statement
		 * or the direct prepared statement. We need the fake prepared statement
		 * because the native compiled statements on Android cannot return
		 * result sets larger than 1x1 and are thus unsuitable for most select
		 * queries.
		 */
		QueryType type = queryType(sql);
		switch (type) {
		default:
		case SELECT:
		case OTHER:
			return new AndroidFakePreparedStatement(database, sql);
		case INSERT:
		case UPDATE:
		case DELETE:
			return new AndroidDirectPreparedStatement(database, sql, type);
		}
	}

	private QueryType queryType(String sql)
	{
		String lower = sql.toLowerCase();
		if (lower.startsWith("select")) {
			return QueryType.SELECT;
		}
		if (lower.startsWith("insert")) {
			return QueryType.INSERT;
		}
		if (lower.startsWith("update")) {
			return QueryType.UPDATE;
		}
		if (lower.startsWith("delete")) {
			return QueryType.DELETE;
		}
		return QueryType.OTHER;
	}

	@Override
	public void execute(String sql) throws QueryException
	{
		database.execSQL(sql);
	}

}

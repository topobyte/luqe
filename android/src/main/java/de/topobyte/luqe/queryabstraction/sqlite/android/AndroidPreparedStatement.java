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

package de.topobyte.luqe.queryabstraction.sqlite.android;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.topobyte.luqe.queryabstraction.sqlite.iface.IPreparedStatement;
import de.topobyte.luqe.queryabstraction.sqlite.iface.IResultSet;
import de.topobyte.luqe.queryabstraction.sqlite.iface.QueryException;

public class AndroidPreparedStatement implements IPreparedStatement
{

	private final SQLiteDatabase database;
	private final String sql;
	private List<String> arguments = null;
	private String[] arrayArguments = null;

	public AndroidPreparedStatement(SQLiteDatabase database, String sql)
	{
		this.database = database;
		this.sql = sql;
	}

	@Override
	public IResultSet executeQuery() throws QueryException
	{
		QueryType type = queryType();
		switch (type) {
		default:
		case SELECT:
		case OTHER:
			String[] array = null;
			if (arrayArguments != null) {
				array = arrayArguments;
			} else if (arguments != null) {
				array = arguments.toArray(new String[0]);
			}
			try {
				Cursor cursor = database.rawQuery(sql, array);
				return new AndroidResultSet(cursor);
			} catch (RuntimeException e) {
				throw new QueryException(e);
			}
		case INSERT:
		case UPDATE:
		case DELETE:
			SQLiteStatement s = database.compileStatement(sql);
			for (int i = 0; i < arguments.size(); i++) {
				String argument = arguments.get(i);
				if (argument == null) {
					s.bindNull(i + 1);
				} else {
					s.bindString(i + 1, argument);
				}
			}
			if (type == QueryType.INSERT) {
				long id = s.executeInsert();
				s.close();
				return new IdResultSet(id);
			} else {
				int nAffected = s.executeUpdateDelete();
				s.close();
				return new EmptyResultSet();
			}
		}
	}

	@Override
	public void execute() throws QueryException
	{
		QueryType type = queryType();
		switch (type) {
		default:
		case SELECT:
		case OTHER:
			String[] array = null;
			if (arrayArguments != null) {
				array = arrayArguments;
			} else if (arguments != null) {
				array = arguments.toArray(new String[0]);
			}
			try {
				if (array == null) {
					database.execSQL(sql);
				} else {
					database.execSQL(sql, array);
				}
			} catch (RuntimeException e) {
				throw new QueryException(e);
			}
			break;
		case INSERT:
		case UPDATE:
		case DELETE:
			SQLiteStatement s = database.compileStatement(sql);
			for (int i = 0; i < arguments.size(); i++) {
				String argument = arguments.get(i);
				if (argument == null) {
					s.bindNull(i + 1);
				} else {
					s.bindString(i + 1, argument);
				}
			}
			if (type == QueryType.INSERT) {
				s.executeInsert();
				s.close();
			} else {
				s.executeUpdateDelete();
				s.close();
			}
			break;
		}
	}

	private QueryType queryType()
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

	private void ensureSize(int position)
	{
		if (arguments == null) {
			arguments = new ArrayList<>();
		}
		while (arguments.size() < position) {
			arguments.add(null);
		}
	}

	@Override
	public void setInt(int position, int value) throws QueryException
	{
		ensureSize(position);
		arguments.set(position - 1, "" + value);
	}

	@Override
	public void setLong(int position, long value) throws QueryException
	{
		ensureSize(position);
		arguments.set(position - 1, "" + value);
	}

	@Override
	public void setDouble(int position, double value) throws QueryException
	{
		ensureSize(position);
		arguments.set(position - 1, "" + value);
	}

	@Override
	public void setString(int position, String value) throws QueryException
	{
		ensureSize(position);
		arguments.set(position - 1, value);
	}

	@Override
	public void setArguments(String[] arguments) throws QueryException
	{
		this.arrayArguments = arguments;
	}

	@Override
	public void close() throws QueryException
	{
		// no-op
	}

}

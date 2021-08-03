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
import android.database.sqlite.SQLiteStatement;
import de.topobyte.luqe.iface.IPreparedStatement;
import de.topobyte.luqe.iface.IResultSet;
import de.topobyte.luqe.iface.QueryException;

public class AndroidDirectPreparedStatement implements IPreparedStatement
{

	private QueryType type;
	private SQLiteStatement stmt;

	public AndroidDirectPreparedStatement(SQLiteDatabase database, String sql,
			QueryType type)
	{
		this.type = type;
		stmt = database.compileStatement(sql);
	}

	@Override
	public IResultSet executeQuery() throws QueryException
	{
		if (type == QueryType.INSERT) {
			long id = stmt.executeInsert();
			return new IdResultSet(id);
		} else {
			stmt.execute();
			return new EmptyResultSet();
		}
	}

	@Override
	public void execute() throws QueryException
	{
		if (type == QueryType.INSERT) {
			stmt.executeInsert();
		} else {
			stmt.executeUpdateDelete();
		}
	}

	@Override
	public void setInt(int position, int value) throws QueryException
	{
		stmt.bindLong(position, value);
	}

	@Override
	public void setLong(int position, long value) throws QueryException
	{
		stmt.bindLong(position, value);
	}

	@Override
	public void setDouble(int position, double value) throws QueryException
	{
		stmt.bindDouble(position, value);
	}

	@Override
	public void setString(int position, String value) throws QueryException
	{
		stmt.bindString(position, value);
	}

	@Override
	public void setBlob(int position, byte[] value) throws QueryException
	{
		stmt.bindBlob(position, value);
	}

	@Override
	public void setBoolean(int position, boolean value) throws QueryException
	{
		stmt.bindLong(position, value ? 1 : 0);
	}

	@Override
	public void setArguments(String[] arguments) throws QueryException
	{
		stmt.bindAllArgsAsStrings(arguments);
	}

	@Override
	public void close() throws QueryException
	{
		stmt.close();
	}

}

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
		return new AndroidPreparedStatement(database, sql);
	}

	@Override
	public void execute(String sql) throws QueryException
	{
		database.execSQL(sql);
	}

}
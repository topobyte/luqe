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

import android.database.Cursor;
import de.topobyte.luqe.queryabstraction.sqlite.iface.IResultSet;
import de.topobyte.luqe.queryabstraction.sqlite.iface.QueryException;

public class AndroidResultSet implements IResultSet
{

	private final Cursor cursor;

	public AndroidResultSet(Cursor cursor)
	{
		this.cursor = cursor;
	}

	@Override
	public boolean next()
	{
		return cursor.moveToNext();
	}

	@Override
	public int getInt(int position) throws QueryException
	{
		return cursor.getInt(position - 1);
	}

	@Override
	public double getDouble(int position) throws QueryException
	{
		return cursor.getDouble(position - 1);
	}

	@Override
	public String getString(int position) throws QueryException
	{
		return cursor.getString(position - 1);
	}

	@Override
	public boolean getBoolean(int position) throws QueryException
	{
		return cursor.getInt(position - 1) != 0;
	}

	@Override
	public void close() throws QueryException
	{
		cursor.close();
	}

}

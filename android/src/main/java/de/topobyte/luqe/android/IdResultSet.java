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

import de.topobyte.luqe.iface.IResultSet;
import de.topobyte.luqe.iface.QueryException;

public class IdResultSet implements IResultSet
{

	private long id;
	private boolean next = false;

	public IdResultSet(long id)
	{
		this.id = id;
	}

	@Override
	public boolean next() throws QueryException
	{
		if (!next) {
			next = true;
			return true;
		}
		return false;
	}

	@Override
	public int getInt(int position) throws QueryException
	{
		throw new QueryException();
	}

	@Override
	public long getLong(int position) throws QueryException
	{
		if (position == 1) {
			return id;
		}
		throw new QueryException();
	}

	@Override
	public String getString(int position) throws QueryException
	{
		throw new QueryException();
	}

	@Override
	public double getDouble(int position) throws QueryException
	{
		throw new QueryException();
	}

	@Override
	public boolean getBoolean(int position) throws QueryException
	{
		throw new QueryException();
	}

	@Override
	public byte[] getBlob(int position) throws QueryException
	{
		throw new QueryException();
	}

	@Override
	public void close() throws QueryException
	{
		// nothing to do here
	}

}

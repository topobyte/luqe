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

package de.topobyte.luqe.queryabstraction.sqlite.iface;

public interface IPreparedStatement
{

	public IResultSet executeQuery() throws QueryException;

	public void execute() throws QueryException;

	public void setInt(int position, int value) throws QueryException;

	public void setLong(int position, long value) throws QueryException;

	public void setDouble(int position, double value) throws QueryException;

	public void setString(int position, String value) throws QueryException;

	public void setArguments(String[] arguments) throws QueryException;
}

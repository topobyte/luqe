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

package de.topobyte.luqe.util;

import java.util.Iterator;

import de.topobyte.luqe.iface.IResultSet;
import de.topobyte.luqe.iface.QueryException;

public class ResultIteration
{

	public static <T> Iterable<T> iterate(IResultSet results,
			ResultSupplier<T> supplier)
	{
		return new Iterable<T>() {

			@Override
			public Iterator<T> iterator()
			{
				return new ResultsIterator<T>(results) {

					@Override
					public T retrieve(IResultSet results) throws QueryException
					{
						return supplier.get(results);
					}

				};
			}

		};
	}

}

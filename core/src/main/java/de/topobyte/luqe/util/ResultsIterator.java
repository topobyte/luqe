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
import java.util.NoSuchElementException;

import de.topobyte.luqe.iface.IResultSet;
import de.topobyte.luqe.iface.QueryException;

public abstract class ResultsIterator<T> implements Iterator<T>
{

	private static enum State {
		NOT_INITIALIZED,
		PULLED,
		RETRIEVED,
		FINISHED
	}

	private IResultSet results;

	private State state = State.NOT_INITIALIZED;
	private T next = null;

	public ResultsIterator(IResultSet results)
	{
		this.results = results;
	}

	public abstract T retrieve(IResultSet results) throws QueryException;

	private void tryFetchNextDependingOnState()
	{
		try {
			fetchNextDependingOnState();
		} catch (QueryException e) {
			throw new RuntimeException("Error while fetching result", e);
		}
	}

	private void fetchNextDependingOnState() throws QueryException
	{
		switch (state) {
		case PULLED:
		case FINISHED: {
			return;
		}
		case NOT_INITIALIZED:
		case RETRIEVED: {
			pull();
			break;
		}
		}
	}

	private void pull() throws QueryException
	{
		if (results.next()) {
			next = retrieve(results);
			state = State.PULLED;
		} else {
			results.close();
			state = State.FINISHED;
		}
	}

	@Override
	public boolean hasNext()
	{
		tryFetchNextDependingOnState();
		return state != State.FINISHED;
	}

	@Override
	public T next()
	{
		tryFetchNextDependingOnState();
		if (state != State.PULLED) {
			throw new NoSuchElementException();
		}
		state = State.RETRIEVED;
		return next;
	}

}

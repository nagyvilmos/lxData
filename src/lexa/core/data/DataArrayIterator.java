/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataArrayIterator.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2015
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ----------   --- ----------  --------------------------------------------------
 * 2016-02-09   WNW 16-01       Make DataArrayIterator public
 *================================================================================
 */
package lexa.core.data;

import java.util.Iterator;

/**
 * Iterate over a {@link DataArray} returning each {@link DataValue} in order.
 * <p>This iterator is not thread safe.
 * It should not be used in cases where the data can be changed externally.  In those cases it is
 * better to retrieve the keys and then loop through them.
 * @author  William
 * @since   2015-03
 */
public class DataArrayIterator
		implements Iterator<DataValue>
{

	/** the data being iterated over. */
	private final DataArray array;
	/** the current position. */
	private int index;

	/**
	 * Create am iterator for a {@link DataArray}.
	 *
	 * @param   array
	 *          the data to iterate.
	 */
	public DataArrayIterator(DataArray array)
	{
		this.array = array;
		this.index = 0;
	}

	@Override
	public boolean hasNext()
	{
		return (this.index < this.array.size());
	}

	@Override
	public DataValue next()
	{
		return this.array.get(this.index++);
	}

	@Override
	public void remove()
	{
		if (this.index == 0 || !this.hasNext())
		{
			throw new IllegalStateException();
		}
		this.index--;
		this.array.remove(this.index);
	}
}

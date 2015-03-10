/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ValueArrayIterator.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2015
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ----------   --- ----------  --------------------------------------------------
 * -            -
 *================================================================================
 */
package lexa.core.data;

import java.util.Iterator;

/**
 * Iterate over a {@link ValueArray} returning each {@link Value} in order.
 * <p>This iterator is not thread safe.
 * It should not be used in cases where the data can be changed externally.  In those cases it is
 * better to retrieve the keys and then loop through them.
 * @author  William
 * @since   2015-03
 */
class ValueArrayIterator
		implements Iterator<Value>
{

	/** the data being iterated over. */
	private final ValueArray array;
	/** the current position. */
	private int index;

	/**
	 * Create am iterator for a {@link ValueArray}.
	 *
	 * @param   array
	 *          the data to iterate.
	 */
	public ValueArrayIterator(ValueArray array)
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
	public Value next()
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

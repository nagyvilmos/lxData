/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataSetIterator.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
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
 * Iterate over a {@DataSet}.
 * <p>This iterator is not thread safe.
 * It should not be used in cases where the data can be changed externally.  In those cases it is
 * better to retrieve the keys and then loop through them.
 * @author  William
 * @since   2013-08
 */
class DataSetIterator
		implements Iterator<DataItem>
{

	/** the data being iterated over. */
	private final DataSet data;
	/** the current position. */
	private int index;

	/**
	 * Create am iterator for a {@link DataSet}.
	 *
	 * @param   data
	 *          the data to iterate.
	 */
	public DataSetIterator(DataSet data)
	{
		this.data = data;
		this.index = 0;
	}

	@Override
	public boolean hasNext()
	{
		return (this.index < this.data.size());
	}

	@Override
	public DataItem next()
	{
		return this.data.get(this.index++);
	}

	@Override
	public void remove()
	{
		if (this.index == 0 || !this.hasNext())
		{
			throw new IllegalStateException();
		}
		this.index--;
		DataItem item = this.data.get(this.index);
		this.data.remove(item.getKey());
	}
}

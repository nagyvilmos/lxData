/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * SealedDataSet.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2013-03-07   WNW 13-03       Tidy up for initial publication to Code project.
 * 2013-07-11   WNW 13-07       Add a null check to stop exceptions in get{key)
 * 2013-07-11   WNW 13-07       Tidy up some appalling JavaDoc.
 *================================================================================
 */
package lexa.core.data;

/**
 * A read only {@link DataSet}.
 *
 * @author  William
 * @since   2009-08
 */
public class SealedDataSet
		extends DataSet
{

	/**
	 * Constructor that wraps the data to be sealed.
	 *
	 * <p>No modification can be made to a sealed data set, though it can be cloned.
	 * <p>The cloned data set can be modified.
	 *
	 * @param   data
	 *          a {@link DataSet} to be sealed.
	 */
	public SealedDataSet(DataSet data)
	{
		super(data);
	}

	/**
	 * Get a DataItem from the list for the supplied key.
	 *
	 * <p>If the value is a {@link DataSet} it is wrapped in a {@link SealedDataSet}.
	 *
	 * @param   key
	 *          the key for the {@link DataItem}.
	 *
	 * @return  the item represented by {@code key}.
	 */
	@Override
	public synchronized DataItem get(String key)
	{
		DataItem item = super.get(key);
		if (item != null && item.getType()
				.equals(ValueType.DATA_SET))
		{
			return new DataItem(item.getKey(), new SealedDataSet(item.getDataSet()));
		}
		return item;
	}

	/**
	 * Put the supplied item into the {@link DataSet}.
	 *
	 * <p>Throws an {@link UnsupportedOperationException} if called.
	 *
	 * @param   item
	 *          a {@link DataItem} to add.
	 * @return  the {@link DataSet} the item was added to.
	 */
	@Override
	public synchronized DataSet put(DataItem item)
	{
		throw new UnsupportedOperationException(
				"Cannot change the content of a sealed data list");
	}

	/**
	 * Removes the specified element from this {@link DataSet}.
	 *
	 * <p>Throws an {@link UnsupportedOperationException} if called.
	 *
	 * @param   key
	 *          the key to the item to remove
	 *
	 * @return  no return is made, this method always throws an exception.
	 */
	@Override
	public synchronized DataItem remove(String key)
	{
		throw new UnsupportedOperationException(
				"Cannot change the content of a sealed data list");
	}
}

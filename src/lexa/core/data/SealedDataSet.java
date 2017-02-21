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
 * 2016-01-28   WNW 16-01       Update javadoc.
 *================================================================================
 */
package lexa.core.data;

/**
 * A read only {@link ArrayDataSet}.
 *
 * @author  William
 * @since   2009-08
 */
public class SealedDataSet
		extends ArrayDataSet
{

	/**
	 * Constructor that wraps the data to be sealed.
	 *
	 * <p>No modification can be made to a sealed data set, though it can be cloned.
	 * <p>The cloned data set can be modified.
	 *
	 * @param   data
	 *          a {@link ArrayDataSet} to be sealed.
	 */
	public SealedDataSet(DataSet data)
	{
		super(data);
	}

	
	/**
	 * Get a ArrayDataItem from the list for the supplied key.
	 *
	 * <p>If the value is a {@link ArrayDataSet} it is wrapped in a {@link SealedDataSet}.
	 *
	 * @param   key
	 *          the key for the {@link ArrayDataItem}.
	 *
	 * @return  the item represented by {@code key}.
	 */
	@Override
	public synchronized DataItem get(String key)
	{
		return sealed(super.get(key));
	}

	@Override
	public synchronized DataItem get(int index)
	{
		return sealed(super.get(index));
	}

	/**
	 * Put the supplied item into the {@link ArrayDataSet}.
	 *
	 * <p>Throws an {@link UnsupportedOperationException} if called.
	 *
	 * @param   item
	 *          a {@link ArrayDataItem} to add.
	 * @return  the {@link ArrayDataSet} the item was added to.
	 */
	@Override
	public synchronized DataSet put(DataItem item)
	{
		throw new UnsupportedOperationException(
				"Cannot change the content of a sealed data list");
	}

	/**
	 * Removes the specified element from this {@link ArrayDataSet}.
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

	/**
	Ensure a {@link DataItem} is sealed
	@param item the item to seal
	@return the sealed item
	*/
	private DataItem sealed(DataItem item)
	{
		if (item != null && item.getType()
				.equals(DataType.DATA_SET))
		{
			return new ArrayDataItem(item.getKey(), new SealedDataSet(item.getDataSet()));
		}
		return item;
	}
}

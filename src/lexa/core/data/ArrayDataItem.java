/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * ArrayDataItem.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: July 2009
 *==============================================================================
 */
package lexa.core.data;

/**
 * A single item in a data set.
 * A single {@link ArrayDataItem} in a {@link ArrayDataSet} represented by a
 * {@link String} key and an {@link Object} value.
 * @author William
 * @since 2009-07
 * @see ArrayDataSet
 */
public class ArrayDataItem
		extends BaseDataItem
{
	/**
	 * Create a new {@link DataItem}.
	 * @param key The key for the item
	 * @param value The value
	 */
	public ArrayDataItem(String key, Object value)
	{
		this(key, new ArrayDataValue(value));
	}
	/**
	 * Create a new {@link DataItem}.
	 * @param key The key for the item
	 * @param value The value
	 */
	public ArrayDataItem(String key, DataValue value)
	{
		super(ArrayFactory.factory, key, value);
	}

	/**
	 * Create a new {@link DataItem}.
	 * This clones an existing data item.
	 * If the intrinsic object is a {@link ArrayDataSet} then that is cloned as well.
	 *
	 * @param clone A {@link DataItem} to clone.
	 * @see ArrayDataSet
	 */
	public ArrayDataItem(DataItem clone)
	{
        super(ArrayFactory.factory, clone.getKey(), clone.getValue());
	}
}

/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * HashDataItem.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 *================================================================================
 */
package lexa.core.data;

/**
 * A single item in a data set.
 * A single {@link HashDataItem} in a {@link HashDataSet} represented by a
 * {@link String} key and an {@link Object} value.
 * @author William
 * @since 2016-09
 */
public class HashDataItem
		extends BaseDataItem
{
	/**
	 * Create a new item
	 * @param key The key for the item
	 * @param value The value
	 */
	public HashDataItem(String key, Object value)
	{
		this(key,new HashDataValue(value));
	}
	/**
	 * Create a new item
	 * @param key The key for the item
	 * @param value The value
	 */
	public HashDataItem(String key, DataValue value)
	{
		super(HashFactory.factory,key,value);

	}

	/**
	 * Create a new item.
	 * This clones an existing data item.
	 * If the intrinsic object is a {@link DataSet} or {@link ValueArray} then
     * that is cloned as well.
	 *
	 * @param clone An item to clone.
	 */
	public HashDataItem(DataItem clone)
	{
            this(clone.getKey(), clone.getValue());
	}

    @Override
    public DataFactory factory()
    {
        return HashFactory.factory;
    }
}

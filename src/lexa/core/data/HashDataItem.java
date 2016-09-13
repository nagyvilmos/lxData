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
 * A single <tt>HashDataItem</tt> in a <tt>HashDataSet</tt> represented by a
 * <tt>String</tt> key and an <tt>Object</tt> value.
 * @author William
 * @since 2016-09
 */
public class HashDataItem
		extends BaseDataItem
{
	/** The value for the item */
	private final Value value;

	/**
	 * Create a new item
	 * @param key The key for the item
	 * @param value The value
	 */
	public HashDataItem(String key, Object value)
	{
		this(key, new HashValue(value));
	}
	/**
	 * Create a new item
	 * @param key The key for the item
	 * @param value The value
	 */
	public HashDataItem(String key, Value value)
	{
		super(key);
		this.value = new HashValue(value);
	}

	/**
	 * Create a new ite,
	 * This clones an existing data item.
	 * If the intrinsic object is a {@link DataSe} or {@link ValueArray} then
     * that is cloned as well.
	 *
	 * @param clone An item to clone.
	 */
	public HashDataItem(DataItem clone)
	{
            this(clone.getKey(), clone.getValue());
	}

	@Override
	public ValueType getType()
	{
		return this.value.getType();
	}

	@Override
	public Value getValue()
	{
		return this.value;
	}
}

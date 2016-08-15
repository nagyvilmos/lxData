/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * SimpleDataItem.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: July 2009
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 13-MAR-13    WNW 2013-03-13  Fix bug in toString() when item name is null.
 * 2013-08-11   WNW -           Kill the integer type.
 *                              I hope that using the long instead will work.
 * 2014-10-10	WNW -           Redo the equals and hash code for SimpleDataItem
 * 2015-03-05	WNW 15-03       Refactor to move values into their own class and 
 *                              add the concept of an array.
 *                              Add ARRAY and LONG types.
 * 2015-03-19	WNW	15-03       Extract interfaces.
 *                              DataItem becomes and interface and SimpleDataItem 
 *                              the default implimentation.
 * 2016-01-27	WNW	16-01       Remove the superfluose clone() method.
 * 2016-01-28   WNW 16-01       Remove the generic getter methods from SimpleDataItem
 *                              into DataItemGetter.
 * 2016-02-09   WNW             Change base abstract classes from *Getter to *Base
 *================================================================================
 */
package lexa.core.data;

/**
 * A single item in a data set.
 * A single <tt>SimpleDataItem</tt> in a <tt>SimpleDataSet</tt> represented by a
 * <tt>String</tt> key and an <tt>Object</tt> value.
 * @author William
 * @since 2009-07
 * @see SimpleDataSet
 */
public class SimpleDataItem
		extends DataItemBase
{

	/** The key for the item */
	private final String key;
	/** The value for the item */
	private final Value value;

	/**
	 * Create a new <tt>DataItem</tt>.
	 * @param key The key for the item
	 * @param value The value
	 */
	public SimpleDataItem(String key, Object value)
	{
		this(key, new SimpleValue(value));
	}
	/**
	 * Create a new <tt>DataItem</tt>.
	 * @param key The key for the item
	 * @param value The value
	 */
	public SimpleDataItem(String key, Value value)
	{
		this.key = key;
		this.value = new SimpleValue(value);
	}

	/**
	 * Create a new <tt>DataItem</tt>.
	 * This clones an existing data item.
	 * If the intrinsic object is a <tt>SimpleDataSet</tt> then that is cloned as well.
	 *
	 * @param clone A <tt>DataItem</tt> to clone.
	 * @see SimpleDataSet
	 */
	public SimpleDataItem(DataItem clone)
	{
            this(clone.getKey(), clone.getValue());
	}

	/**
	 * Gets the key of a <tt>SimpleDataItem</tt>.
	 * @return The item's key
	 */
	@Override
	public String getKey()
	{
		return this.key;
	}

	/**
	 * Gets the value as a string.
	 * @return The value as a string.
	 */
	@Override
	public String getString()
	{
		return this.value.getString();
	}

	/**
	 * Gets the type of the value.
	 * @return The type of the value.
	 */
	@Override
	public ValueType getType()
	{
		return this.value.getType();
	}

	/**
	 * Gets the value of a <tt>SimpleDataItem</tt>.
	 * @return The value of the item.
	 */
	@Override
	public Value getValue()
	{
		return this.value;
	}
}

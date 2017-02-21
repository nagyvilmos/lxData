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
 * 2016-09-09   WNW 16-09       Change base abstract classes from *Base to Base*
 * 2016-09-12   WNW 16-09       Move key into BaseDataItem
 * 2016-09-13   WNW 16-09       Remove getString as it duplicates BaseDataItem
 *================================================================================
 */
package lexa.core.data;

/**
 * A single item in a data set.
 * A single {@link SimpleDataItem} in a {@link SimpleDataSet} represented by a
 * {@link String} key and an {@link Object} value.
 * @author William
 * @since 2009-07
 * @see SimpleDataSet
 */
public class SimpleDataItem
		extends BaseDataItem
{
	/** The value for the item */
	private final DataValue value;

	/**
	 * Create a new {@link DataItem}.
	 * @param key The key for the item
	 * @param value The value
	 */
	public SimpleDataItem(String key, Object value)
	{
		this(key, new SimpleValue(value));
	}
	/**
	 * Create a new {@link DataItem}.
	 * @param key The key for the item
	 * @param value The value
	 */
	public SimpleDataItem(String key, DataValue value)
	{
		super(key);
		this.value = new SimpleValue(value);
	}

	/**
	 * Create a new {@link DataItem}.
	 * This clones an existing data item.
	 * If the intrinsic object is a {@link SimpleDataSet} then that is cloned as well.
	 *
	 * @param clone A {@link DataItem} to clone.
	 * @see SimpleDataSet
	 */
	public SimpleDataItem(DataItem clone)
	{
            this(clone.getKey(), clone.getValue());
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
	 * Gets the value of a {@link SimpleDataItem}.
	 * @return The value of the item.
	 */
	@Override
	public DataValue getValue()
	{
		return this.value;
	}
}

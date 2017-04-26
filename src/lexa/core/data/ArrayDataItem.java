/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ArrayDataItem.java
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
 * 2014-10-10	WNW -           Redo the equals and hash code for ArrayDataItem
 * 2015-03-05	WNW 15-03       Refactor to move values into their own class and
 *                              add the concept of an array.
 *                              Add ARRAY and LONG types.
 * 2015-03-19	WNW	15-03       Extract interfaces.
 *                              DataItem becomes and interface and ArrayDataItem
 *                              the default implimentation.
 * 2016-01-27	WNW	16-01       Remove the superfluose clone() method.
 * 2016-01-28   WNW 16-01       Remove the generic getter methods from ArrayDataItem
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
 * A single {@link ArrayDataItem} in a {@link SimpleDataSet} represented by a
 * {@link String} key and an {@link Object} value.
 * @author William
 * @since 2009-07
 * @see SimpleDataSet
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
	 * If the intrinsic object is a {@link SimpleDataSet} then that is cloned as well.
	 *
	 * @param clone A {@link DataItem} to clone.
	 * @see SimpleDataSet
	 */
	public ArrayDataItem(DataItem clone)
	{
        super(ArrayFactory.factory, clone.getKey(), clone.getValue());
	}

    @Override
    public DataFactory factory()
    {
        return ArrayFactory.factory;
    }
}

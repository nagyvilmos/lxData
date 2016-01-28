/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * SimpleDataSet.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: April 2015
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ----------   --- ----------  --------------------------------------------------
 * 2015-04-22	WNW				More refactoring
 * 2016-01-27	WNW	16-01       Remove the superfluose clone() method.
 * 2016-01-28   WNW 16-01       Add method getType(String)
 * 2016-01-28   WNW 16-01       Move toString() from SimpleDataSet to DataSetGetter
 *                              Fix a bug in toString() for 0 item set
 *================================================================================
 */
package lexa.core.data;

import java.util.Date;
import java.util.Iterator;

/**
 * Provide the type safe getters for a data set.
 * <p>
 * This provides the getters for the specific types supported.
 * It does not implement the basic {@link DataSet#get(java.lang.String) get} method
 * @author william
 */
public abstract class DataSetGetter
		implements DataSet
{
	/**
	 * Get a {@link ValueArray} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link ValueArray} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized ValueArray getArray(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getArray();
	}
	/**
	 * Get a {@link Boolean} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link String} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized Boolean getBoolean(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getBoolean();
	}

	/**
	 * Get a {@link SimpleDataSet} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link SimpleDataSet} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized DataSet getDataSet(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getDataSet();
	}

	/**
	 * Get a {@link Date} from the list for the supplied key.
	 * @param key The key for the {@link Date}.
	 * @return If the item exists and is a {@link Date} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized Date getDate(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getDate();
	}

	/**
	 * Get a {@link Double} from the list for the supplied key.
	 * @param   key
	 *          the key for the {@link Double}.
	 * @return  If the item exists and is a {@link Double}
	 *          then the item's value,
	 *          otherwise {@code null}.
	 */
	@Override
	public synchronized Double getDouble(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getDouble();
	}

	/**
	 * Get an {@link Integer} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is an {@link Integer} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized Integer getInteger(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getInteger();
	}

	/**
	 * Get a {@link Long} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link Long} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized Long getLong(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getLong();
	}

	/**
	 * Get a {@link String} from the list for the supplied key.
	 * @param key The key for the {@link SimpleDataItem}.
	 * @return If the item exists and is a {@link String} then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized String getString(String key)
	{
		DataItem item = this.get(key);
		return (item == null) ?
				null :
				item.getString();
	}

	/**
	 * Get the type of an item from the list for the supplied key.
	 * @param key The key for the {@link Object}.
	 * @return If the item exists then the
	 * item's type, otherwise {@link ValueType#NULL}.
	 */
	@Override
	public synchronized ValueType getType(String key)
	{
		DataItem item = this.get(key);
		if (item == null)
		{
			return ValueType.NULL;
		}
		return item.getType();
	}

    /**
	 * Get an {@link Object} from the list for the supplied key.
	 * @param key The key for the {@link Object}.
	 * @return If the item exists then the
	 * item's value, otherwise {@code null}.
	 */
	@Override
	public synchronized Object getValue(String key)
	{
		DataItem item = this.get(key);
		if (item == null)
		{
			return null;
		}
		return item.getValue();
	}

	/**
	 * Return a string representation of a {@link SimpleDataSet}.
	 * Formatted as a list of all the {@link SimpleDataItem}'s:
	 * <blockquote>
	 * <pre>
	 * {{key}{value} {key}{{key}{value} {key}{value}}}
	 * </pre></blockquote>
	 *
	 * @return A string representation of the object.
	 */
	@Override
	public synchronized String toString()
	{
		StringBuilder sb = new StringBuilder("{");
		for (DataItem item
				: this)
		{
			sb.append(item.toString()).append(" ");			
		}
        int length = sb.length();
        if (length > 1)
        {
            sb.deleteCharAt(length - 1);
        }
		return sb.append("}")
				.toString();
	}

    @Override
	public Iterator<DataItem> iterator()
	{
		return new DataSetIterator(this);
	}

}

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
 * 2014-10-10	WNW -			Redo the equals and hash code for SimpleDataItem
 * 2015-03-05	WNW 15-03		Refactor to move values into their own class and 
 *								add the concept of an array.
 *								Add ARRAY and LONG types.
 * 2015-03-19	WNW	15-03		Extract interfaces.
 *								DataItem becomes and interface and SimpleDataItem 
 *								the default implimentation.
 *================================================================================
 */
package lexa.core.data;

import java.util.Date;

/**
 * A single item in a data set.
 * A single <tt>SimpleDataItem</tt> in a <tt>SimpleDataSet</tt> represented by a
 * <tt>String</tt> key and an <tt>Object</tt> value.
 * @author William
 * @since 2009-07
 * @see SimpleDataSet
 */
public class SimpleDataItem
		implements DataItem
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
		this.value = value != null ? value
				: new SimpleValue(null);
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
		this.key = clone.getKey();
		this.value = clone.getValueObject().clone();
	}

	/**
	 * Compares this to another object.
	 * If the other object is a {@code SimpleDataItem}, compare the name and value for equality.
	 * @param obj Another object to compare
	 * @return True if the object is a {@code SimpleDataItem} with the same name and value.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || this.getClass() != obj.getClass())
		{
			return false;
		}
		final SimpleDataItem other = (SimpleDataItem)obj;
		return (this.key == null) ? (other.key == null) : this.key.equals(other.key) &&
				(this.value == null) ? (other.value == null) : this.value.equals(other.value);
	}

	/**
	 * Gets the value as an array.
	 * @return The value as an array.
	 */
	@Override
	public ValueArray getArray()
	{
		return this.value.getArray();
	}

	/**
	 * Gets the value as a boolean.
	 * @return The value as a boolean.
	 */
	@Override
	public Boolean getBoolean()
	{
		return this.value.getBoolean();
	}

	/**
	 * Gets the value as a data set.
	 * @return The value as a data set.
	 */
	@Override
	public DataSet getDataSet()
	{
		return this.value.getDataSet();
	}

	/**
	 * Gets the value as a data.
	 * @return The value as a date.
	 */
	@Override
	public Date getDate()
	{
		return this.value.getDate();
	}

	/**
	 * Gets the value as a double.
	 * @return The value as a double.
	 */
	@Override
	public Double getDouble()
	{
		return this.value.getDouble();
	}

	/**
	 * Gets the value as an integer.
	 * @return The value as an integer.
	 */
	@Override
	public Integer getInteger()
	{
		return this.value.getInteger();
	}

	/**
	 * Gets the value as a long.
	 * @return The value as a long.
	 */
	@Override
	public Long getLong()
	{
		return this.value.getLong();
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
	public Object getValue()
	{
		return this.value.getValue();
	}

	/**
	 * Gets the value of a <tt>SimpleDataItem</tt>.
	 * @return The value of the item.
	 */
	@Override
	public Value getValueObject()
	{
		return this.value;
	}
	/**
	 * Get the hash code of the data item.
	 *
	 * @return The hash code of the data item.
	 */
	@Override
	public int hashCode()
	{
		return (this.key == null ? 3 : (this.key.hashCode() * 3)) +
				(this.value.hashCode());
	}

	/***
	 * Get the <tt>SimpleDataItem</tt> as a string description.
	 * <tt>toString</tt> is in the format of:
	 * <pre>key {value}</pre>
	 * @return The item's string representation.
	 */
	@Override
	public String toString()
	{
		return "{" + this.key + " " + this.value.toString() + "}";
	}

	@Override
	public DataItem clone()
	{
		return new SimpleDataItem(this);
	}
}

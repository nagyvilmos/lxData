/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataItem.java
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
 * 2014-10-10	WNW -			Redo the equals and hash code for DataItem
 * 2015-03-05	WNW 15-03		Refactor to move values into their own class and 
 *								add the concept of an array.
 *								Add ARRAY and LONG types.
 *================================================================================
 */
package lexa.core.data;

import java.util.Date;

/**
 * A single item in a data set.
 * A single <tt>DataItem</tt> in a <tt>DataSet</tt> represented by a
 * <tt>String</tt> key and an <tt>Object</tt> value.
 * @author William
 * @since 2009-07
 * @see DataSet
 */
public class DataItem
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
	public DataItem(String key, Object value)
	{
		this(key, new Value(value));
	}
	/**
	 * Create a new <tt>DataItem</tt>.
	 * @param key The key for the item
	 * @param value The value
	 */
	public DataItem(String key, Value value)
	{
		this.key = key;
		this.value = value != null ? value
				: new Value(null);
	}

	/**
	 * Create a new <tt>DataItem</tt>.
	 * This clones an existing data item.
	 * If the intrinsic object is a <tt>DataSet</tt> then that is cloned as well.
	 *
	 * @param clone A <tt>DataItem</tt> to clone.
	 * @see DataSet
	 */
	public DataItem(DataItem clone)
	{
		this.key = clone.key;
		if (ValueType.DATA_SET.equals(clone.getType()))
		{
			this.value = new Value(new DataSet(clone.value.getDataSet()));
		}
		else
		{
			this.value = new Value(clone.value.getValue());
		}
	}

	/**
	 * Compares this to another object.
	 * If the other object is a {@code DataItem}, compare the name and value for equality.
	 * @param obj Another object to compare
	 * @return True if the object is a {@code DataItem} with the same name and value.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || this.getClass() != obj.getClass())
		{
			return false;
		}
		final DataItem other = (DataItem)obj;
		return (this.key == null) ? (other.key == null) : this.key.equals(other.key) &&
				(this.value == null) ? (other.value == null) : this.value.equals(other.value);
	}

	/**
	 * Gets the value as a boolean.
	 * @return The value as a boolean.
	 */
	public ValueArray getArray()
	{
		return this.value.getArray();
	}

	/**
	 * Gets the value as a boolean.
	 * @return The value as a boolean.
	 */
	public Boolean getBoolean()
	{
		return this.value.getBoolean();
	}

	/**
	 * Gets the value as a data set.
	 * @return The value as a data set.
	 */
	public DataSet getDataSet()
	{
		return this.value.getDataSet();
	}

	/**
	 * Gets the value as a data.
	 * @return The value as a date.
	 */
	public Date getDate()
	{
		return this.value.getDate();
	}

	/**
	 * Gets the value as a double.
	 * @return The value as a double.
	 */
	public Double getDouble()
	{
		return this.value.getDouble();
	}

	/**
	 * Gets the value as an integer.
	 * @return The value as an integer.
	 */
	public Integer getInteger()
	{
		return this.value.getInteger();
	}

	/**
	 * Gets the value as a long.
	 * @return The value as a long.
	 */
	public Long getLong()
	{
		return this.value.getLong();
	}

	/**
	 * Gets the key of a <tt>DataItem</tt>.
	 * @return The item's key
	 */
	public String getKey()
	{
		return this.key;
	}

	/**
	 * Gets the value as a string.
	 * @return The value as a string.
	 */
	public String getString()
	{
		return this.value.getString();
	}

	/**
	 * Gets the type of the value.
	 * @return The type of the value.
	 */
	public ValueType getType()
	{
		return this.value.getType();
	}

	/**
	 * Gets the value of a <tt>DataItem</tt>.
	 * @return The value of the item.
	 */
	public Object getValue()
	{
		return this.value.getValue();
	}

	/**
	 * Gets the value of a <tt>DataItem</tt>.
	 * @return The value of the item.
	 */
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
	 * Get the <tt>DataItem</tt> as a string description.
	 * <tt>toString</tt> is in the format of:
	 * <pre>key {value}</pre>
	 * @return The item's string representation.
	 */
	@Override
	public String toString()
	{
		return "{" + this.key + " " + this.value.toString() + "}";
	}
}

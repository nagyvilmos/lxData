/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataItemGetter.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: January 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-01-28   WNW 16-01       Remove the generic getter methods from SimpleDataItem
 *                              into DataItemGetter.
 * 2016-02-09   WNW             Change base abstract classes from *Getter to *Base
 *================================================================================
 */
package lexa.core.data;

import java.util.Date;

/**
 * Provide the type safe getters for a data item.
 * <p>
 * This provides the getters for the specific types supported.
 * It does not implement the basic {@link DataItem#getValue() getValueObject} method.
 * @author william
 * @since 2016-01
 */
@SuppressWarnings("EqualsAndHashcode")
public abstract class DataItemBase
        implements DataItem 
{

	/**
	 * Compares this to another object.
	 * If the other object is a {@code DataItem}, compare the name and value for equality.
	 * @param obj Another object to compare
	 * @return True if the object is a {@code DataItem} with the same name and value.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !DataItem.class.isAssignableFrom(obj.getClass()))
		{
			return false;
		}
		final DataItem other = (DataItem)obj;
		return (this.getKey() == null) ?
                        (other.getKey() == null) :
                        this.getKey().equals(other.getKey()) &&
				(this.getObject() == null) ?
                        (other.getObject() == null) :
                        this.getObject().equals(other.getObject());
	}
    
	/**
	 * Gets the value as an array.
	 * @return The value as an array.
	 */
	@Override
	public ValueArray getArray()
	{
		return this.getValue().getArray();
	}

	/**
	 * Gets the value as a boolean.
	 * @return The value as a boolean.
	 */
	@Override
	public Boolean getBoolean()
	{
		return this.getValue().getBoolean();
	}

	/**
	 * Gets the value as a data set.
	 * @return The value as a data set.
	 */
	@Override
	public DataSet getDataSet()
	{
		return this.getValue().getDataSet();
	}

	/**
	 * Gets the value as a data.
	 * @return The value as a date.
	 */
	@Override
	public Date getDate()
	{
		return this.getValue().getDate();
	}

	/**
	 * Gets the value as a double.
	 * @return The value as a double.
	 */
	@Override
	public Double getDouble()
	{
		return this.getValue().getDouble();
	}

	/**
	 * Gets the value as an integer.
	 * @return The value as an integer.
	 */
	@Override
	public Integer getInteger()
	{
		return this.getValue().getInteger();
	}

	/**
	 * Gets the value as a long.
	 * @return The value as a long.
	 */
	@Override
	public Long getLong()
	{
		return this.getValue().getLong();
	}

	/**
	 * Gets the value as a string.
	 * @return The value as a string.
	 */
	@Override
	public String getString()
	{
		return this.getValue().getString();
	}

	/**
	 * Gets the type of the value.
	 * @return The type of the value.
	 */
	@Override
	public ValueType getType()
	{
		return this.getValue().getType();
	}

	/**
	 * Gets the underlying object of a {@see DataItem}.
	 * @return The underlying object of the item.
	 */
	@Override
	public Object getObject()
	{
		return this.getValue().getObject();
	}

	/**
	 * Get the hash code of the data item.
	 *
	 * @return The hash code of the data item.
	 */
	@Override
	public int hashCode()
	{
		return (this.getKey() == null ? 3 : (this.getKey().hashCode() * 3)) +
				(this.getValue().hashCode());
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
		return "{" + this.getKey() + " " + this.getValue().toString() + "}";
	}
}

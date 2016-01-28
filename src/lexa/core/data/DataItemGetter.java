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
 *================================================================================
 */
package lexa.core.data;

import java.util.Date;

/**
 * Provide the type safe getters for a data item.
 * <p>
 * This provides the getters for the specific types supported.
 * It does not implement the basic {@link DataItem#getValueObject() getValueObject} method.
 * @author william
 * @since 2016-01
 */
@SuppressWarnings("EqualsAndHashcode")
public abstract class DataItemGetter
        implements DataItem 
{

	/**
	 * Gets the value as an array.
	 * @return The value as an array.
	 */
	@Override
	public ValueArray getArray()
	{
		return this.getValueObject().getArray();
	}

	/**
	 * Gets the value as a boolean.
	 * @return The value as a boolean.
	 */
	@Override
	public Boolean getBoolean()
	{
		return this.getValueObject().getBoolean();
	}

	/**
	 * Gets the value as a data set.
	 * @return The value as a data set.
	 */
	@Override
	public DataSet getDataSet()
	{
		return this.getValueObject().getDataSet();
	}

	/**
	 * Gets the value as a data.
	 * @return The value as a date.
	 */
	@Override
	public Date getDate()
	{
		return this.getValueObject().getDate();
	}

	/**
	 * Gets the value as a double.
	 * @return The value as a double.
	 */
	@Override
	public Double getDouble()
	{
		return this.getValueObject().getDouble();
	}

	/**
	 * Gets the value as an integer.
	 * @return The value as an integer.
	 */
	@Override
	public Integer getInteger()
	{
		return this.getValueObject().getInteger();
	}

	/**
	 * Gets the value as a long.
	 * @return The value as a long.
	 */
	@Override
	public Long getLong()
	{
		return this.getValueObject().getLong();
	}

	/**
	 * Gets the value as a string.
	 * @return The value as a string.
	 */
	@Override
	public String getString()
	{
		return this.getValueObject().getString();
	}

	/**
	 * Gets the type of the value.
	 * @return The type of the value.
	 */
	@Override
	public ValueType getType()
	{
		return this.getValueObject().getType();
	}

	/**
	 * Gets the value of a <tt>SimpleDataItem</tt>.
	 * @return The value of the item.
	 */
	@Override
	public Object getValue()
	{
		return this.getValueObject().getValue();
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
				(this.getValueObject().hashCode());
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
		return "{" + this.getKey() + " " + this.getValueObject().toString() + "}";
	}

    
}

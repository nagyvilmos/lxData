/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * SimpleValue.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2015
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 *================================================================================
 */
package lexa.core.data;

import java.util.Date;

/**
 A single value for a {@link SimpleDataSet}
 <p>
 This is a type safe representation of an object used by a {@link SimpleDataSet},
 {@link DataValue} or {@link ValueArray}.
 Even if a {@code null} is added, internally it is represented by an object, this 
 makes certain operations easier internally.
 <p>
 This class manages the type conversion and keels it type safe.
 @author william
 @since 2015-03
 */
public class SimpleValue implements Value
{
    /** the value being represented */
	private final Object value;
	
	/**
	Create a value to represent an object
	@param value the contained value
	*/
	SimpleValue(Object value)
	{
		this.value = value;
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
		final SimpleValue other = (SimpleValue)obj;
		return (this.value == null) ?
				(other.value == null) :
				this.value.equals(other.value);
	}
	
	/**
	 * Gets the value as a {@link ValueArray}.
	 * @return The value as am array.
	 */
	@Override
	public ValueArray getArray()
	{
		return (ValueArray) ValueType.ARRAY.getValueIfType(this.value);
	}
	/**
	 * Gets the value as a boolean.
	 * @return The value as a boolean.
	 */
	@Override
	public Boolean getBoolean()
	{
		return (Boolean) ValueType.BOOLEAN.getValueIfType(this.value);
	}

	/**
	 * Gets the value as a data set.
	 * @return The value as a data set.
	 */
	@Override
	public DataSet getDataSet()
	{
		return (SimpleDataSet) ValueType.DATA_SET.getValueIfType(this.value);
	}

	/**
	 * Gets the value as a data.
	 * @return The value as a date.
	 */
	@Override
	public Date getDate()
	{
		return (Date) ValueType.DATE.getValueIfType(this.value);
	}

	/**
	 * Gets the value as a double.
	 * @return The value as a double.
	 */
	@Override
	public Double getDouble()
	{
		return (Double) ValueType.DOUBLE.getValueIfType(this.value);
	}

	/**
	 * Gets the value as an integer.
	 * @return The value as an integer.
	 */
	@Override
	public Integer getInteger()
	{
		return (Integer) ValueType.INTEGER.getValueIfType(this.value);
	}
	/**
	 * Gets the value as an integer.
	 * @return The value as an integer.
	 */
	@Override
	public Long getLong()
	{
		return (Long) ValueType.LONG.getValueIfType(this.value);
	}

	/**
	 * Gets the value as a string.
	 * @return The value as a string.
	 */
	@Override
	public String getString()
	{
		return (String) ValueType.STRING.getValueIfType(this.value);
	}

	/**
	 * Gets the type of the value.
	 * @return The type of the value.
	 */
	@Override
	public ValueType getType()
	{
		return ValueType.getType(this.value);
	}

	/**
	 * Gets the internal value.
	 * @return The value of the item.
	 */
	@Override
	public Object getValue()
	{
		return this.value;
	}

	/**
	 * Get the hash code of the value.
	 *
	 * @return The hash code of the value.
	 */
	@Override
	public int hashCode()
	{
		return (this.value == null ? 0 : this.value.hashCode());
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (this.value == null)
		{
			sb.append("[null]");
		}
		else
		{
			sb.append(this.getType().getTypeChar())
					.append(' ')
					.append(this.value);
		}
		return sb.toString();
	}

	@Override
	public Value clone()
	{
		if (ValueType.DATA_SET.equals(this.getType()))
		{
			return new SimpleValue(this.getDataSet().clone());
		}
		if (ValueType.ARRAY.equals(this.getType()))
		{
			return new SimpleValue(this.getArray().clone());
		}
		return new SimpleValue(this.getValue());
	}
}

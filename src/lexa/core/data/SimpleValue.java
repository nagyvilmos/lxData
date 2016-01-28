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
 * 2016-01-27	WNW	16-01       Remove the superfluose clone() method.
 *                              Add in a cloning constructor.
 * 2016-01-28   WNW 16-01       Update javadoc.
 *================================================================================
 */
package lexa.core.data;

import java.util.Date;

/**
 Implementation of {@link Value} for use in a {@link SimpleDataSet}
 <p>
 This class manages the type conversion and keels it type safe.
 @author william
 @since 2015-03
 */
public class SimpleValue extends ValueGetter implements Value
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
	Create a value as a clone of another value
	@param clone a Value to clone
	*/
	SimpleValue(Value clone)
	{
            ValueType type = (clone != null) ?
                    clone.getType() :
                    ValueType.NULL;
            this.value =
                    type.equals(ValueType.NULL) ? 
                        null :
                    type.equals(ValueType.ARRAY) ? 
                        new SimpleValueArray(clone.getArray()) :
                    type.equals(ValueType.DATA_SET) ?
                        new SimpleDataSet(clone.getDataSet()) :
                    clone.getValue();
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
}

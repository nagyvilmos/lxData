/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * BaseValue.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: January 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-01-28   WNW 16-01       Remove the generic getter methods from SimpleValue
 *                              into ValueGetter.
 * 2016-02-09   WNW             Change base abstract classes from *Getter to *Base
 * 2016-09-09   WNW 16-09       Change base abstract classes from *Base to Base*
 *================================================================================
 */
package lexa.core.data;

import java.util.Date;

/**
 * Provide the type safe getters for a value.
 * <p>
 * This provides the getters for the specific types supported.
 * It does not implement the basic {@link DataValue#getObject() getValue} method
 * @author william
 * @since 2016-01
 */
public abstract class BaseValue
        implements DataValue
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
		if (obj == null || !DataValue.class.isAssignableFrom(obj.getClass()))
		{
			return false;
		}
		final DataValue other = (DataValue)obj;
		return (this.getObject() == null) ?
				(other.getObject() == null) :
				this.getObject().equals(other.getObject());
	}

    /**
     * Gets the value as a {@link DataArray}.
     * @return The value as am array.
     */
    @Override
    public DataArray getArray() {
        return (DataArray) ValueType.ARRAY.getValueIfType(this.getObject());
    }

    /**
     * Gets the value as a boolean.
     * @return The value as a boolean.
     */
    @Override
    public Boolean getBoolean() {
        return (Boolean) ValueType.BOOLEAN.getValueIfType(this.getObject());
    }

    /**
     * Gets the value as a data set.
     * @return The value as a data set.
     */
    @Override
    public DataSet getDataSet() {
        return (DataSet) ValueType.DATA_SET.getValueIfType(this.getObject());
    }

    /**
     * Gets the value as a data.
     * @return The value as a date.
     */
    @Override
    public Date getDate() {
        return (Date) ValueType.DATE.getValueIfType(this.getObject());
    }

    /**
     * Gets the value as a double.
     * @return The value as a double.
     */
    @Override
    public Double getDouble() {
        return (Double) ValueType.DOUBLE.getValueIfType(this.getObject());
    }

    /**
     * Gets the value as an integer.
     * @return The value as an integer.
     */
    @Override
    public Integer getInteger() {
        return (Integer) ValueType.INTEGER.getValueIfType(this.getObject());
    }

    /**
     * Gets the value as an integer.
     * @return The value as an integer.
     */
    @Override
    public Long getLong() {
        return (Long) ValueType.LONG.getValueIfType(this.getObject());
    }

    /**
     * Gets the value as a string.
     * @return The value as a string.
     */
    @Override
    public String getString() {
        return (String) ValueType.STRING.getValueIfType(this.getObject());
    }
    
	

	/**
	 * Gets the type of the value.
	 * @return The type of the value.
	 */
	@Override
	public ValueType getType()
	{
		return ValueType.getType(this.getObject());
	}

	/**
	 * Get the hash code of the value.
	 *
	 * @return The hash code of the value.
	 */
	@Override
	public int hashCode()
	{
		return (this.getObject() == null ? 0 : this.getObject().hashCode());
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (this.getObject() == null)
		{
			sb.append("[null]");
		}
		else
		{
			sb.append(this.getType().getTypeChar())
					.append(' ')
					.append(this.getObject());
		}
		return sb.toString();
	}
}

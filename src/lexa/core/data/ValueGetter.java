/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ValueGetter.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: January 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-01-28   WNW 16-01       Remove the generic getter methods from SimpleValue
 *                              into ValueGetter.
 *================================================================================
 */
package lexa.core.data;

import java.util.Date;

/**
 * Provide the type safe getters for a value.
 * <p>
 * This provides the getters for the specific types supported.
 * It does not implement the basic {@link Value#getValue() getValue} method
 * @author william
 * @since 2016-01
 */
@SuppressWarnings("EqualsAndHashcode")
public abstract class ValueGetter
        implements Value
{
    
    /**
     * Gets the value as a {@link ValueArray}.
     * @return The value as am array.
     */
    @Override
    public ValueArray getArray() {
        return (ValueArray) ValueType.ARRAY.getValueIfType(this.getValue());
    }

    /**
     * Gets the value as a boolean.
     * @return The value as a boolean.
     */
    @Override
    public Boolean getBoolean() {
        return (Boolean) ValueType.BOOLEAN.getValueIfType(this.getValue());
    }

    /**
     * Gets the value as a data set.
     * @return The value as a data set.
     */
    @Override
    public DataSet getDataSet() {
        return (SimpleDataSet) ValueType.DATA_SET.getValueIfType(this.getValue());
    }

    /**
     * Gets the value as a data.
     * @return The value as a date.
     */
    @Override
    public Date getDate() {
        return (Date) ValueType.DATE.getValueIfType(this.getValue());
    }

    /**
     * Gets the value as a double.
     * @return The value as a double.
     */
    @Override
    public Double getDouble() {
        return (Double) ValueType.DOUBLE.getValueIfType(this.getValue());
    }

    /**
     * Gets the value as an integer.
     * @return The value as an integer.
     */
    @Override
    public Integer getInteger() {
        return (Integer) ValueType.INTEGER.getValueIfType(this.getValue());
    }

    /**
     * Gets the value as an integer.
     * @return The value as an integer.
     */
    @Override
    public Long getLong() {
        return (Long) ValueType.LONG.getValueIfType(this.getValue());
    }

    /**
     * Gets the value as a string.
     * @return The value as a string.
     */
    @Override
    public String getString() {
        return (String) ValueType.STRING.getValueIfType(this.getValue());
    }
    
	

	/**
	 * Gets the type of the value.
	 * @return The type of the value.
	 */
	@Override
	public ValueType getType()
	{
		return ValueType.getType(this.getValue());
	}

	/**
	 * Get the hash code of the value.
	 *
	 * @return The hash code of the value.
	 */
	@Override
	public int hashCode()
	{
		return (this.getValue() == null ? 0 : this.getValue().hashCode());
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (this.getValue() == null)
		{
			sb.append("[null]");
		}
		else
		{
			sb.append(this.getType().getTypeChar())
					.append(' ')
					.append(this.getValue());
		}
		return sb.toString();
	}
}

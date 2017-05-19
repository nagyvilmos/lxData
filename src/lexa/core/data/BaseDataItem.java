/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * BaseDataItem.java
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
 * 2016-09-09   WNW 16-09       Change base abstract classes from *Base to Base*
 * 2016-09-12   WNW 16-09       Move key into BaseDataItem
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
public abstract class BaseDataItem
        implements DataItem
{
    private final DataFactory factory;
	/** The key for the item */
	private final String key;
	/** The value for the item */
	private final DataValue value;
    /**
     * Base for new data item
     * <br>
     * This handles only the key, the concrete classes need to handle the value
     * @param factory
     *          the factory for the item
     * @param key
     *          the key for the item
     * @param value
     *          the value for the item
     */
    protected BaseDataItem(DataFactory factory, String key, DataValue value)
    {
        this.factory = factory;
        this.key = key;
        this.value =  this.factory.convert(value);
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

    @Override
    public DataFactory factory()
    {
        return this.factory;
    }
	/**
	 * Gets the value as an array.
	 * @return The value as an array.
	 */
	@Override
	public DataArray getArray()
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

	@Override
	public String getKey()
	{
		return this.key;
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
	public DataType getType()
	{
		return this.value.getType();
	}

	/**
	 * Gets the value of a {@link ArrayDataItem}.
	 * @return The value of the item.
	 */
	@Override
	public DataValue getValue()
	{
		return this.value;
	}

	/**
	 * Gets the underlying object of a {@link DataItem}.
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
	 * Get the {@link SimpleDataItem} as a string description.
	 * {@link toString} is in the format of:
	 * <pre>key {value}</pre>
	 * @return The item's string representation.
	 */
	@Override
	public String toString()
	{
		return "{" + this.getKey() + " " + this.getValue().toString() + "}";
	}
}

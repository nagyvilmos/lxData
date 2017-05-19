/*
 *==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * DataItem.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2015
 *------------------------------------------------------------------------------
 *==============================================================================
 */
package lexa.core.data;

import java.util.Date;

/**
 * Interface for a single DataItem
 * @author william
 * @since 2015-03
 */
public interface DataItem
{
    /**
     * Get the factory used to create this type of {@link DataItem}.
     * @return the factory used to create this type of {@link DataItem}
     */
    public DataFactory factory();

	/**
	 * Gets the value as an array.
	 * @return The value as an array.
	 */
	DataArray getArray();

	/**
	 * Gets the value as a boolean.
	 * @return The value as a boolean.
	 */
	Boolean getBoolean();

	/**
	 * Gets the value as a data set.
	 * @return The value as a data set.
	 */
	DataSet getDataSet();

	/**
	 * Gets the value as a date.
	 * @return The value as a date.
	 */
	Date getDate();

	/**
	 * Gets the value as a double.
	 * @return The value as a double.
	 */
	Double getDouble();

	/**
	 * Gets the value as an integer.
	 * @return The value as an integer.
	 */
	Integer getInteger();

	/**
	 * Gets the key of a {@link DataItem}.
	 * @return The item's key
	 */
	String getKey();

	/**
	 * Gets the value as a long.
	 * @return The value as a long.
	 */
	Long getLong();

	/**
	 * Gets the value as a string.
	 * @return The value as a string.
	 */
	String getString();

	/**
	 * Gets the type of the value.
	 * @return The type of the value.
	 */
	DataType getType();

	/**
	 * Gets the underlying object.
	 * @return The underlying object of the item.
	 */
	Object getObject();

	/**
	 * Gets the {@link DataValue} of the item.
	 * @return The {@link DataValue} of the item.
	 */
	DataValue getValue();

}

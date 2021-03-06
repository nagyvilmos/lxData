/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * DataValue.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2015
 *==============================================================================
 */
package lexa.core.data;

import java.util.Date;

/**
 * Interface for a value within a {@link DataSet}
 <p>
 This is a type safe representation of an object used by a {@link DataSet},
 {@link DataItem} or {@link DataArray}.
 Even if a {@code null} is added, internally it is represented by an object, this
 makes certain operations easier internally.
 @author william
 @since 2015-03
 */
public interface DataValue
        extends Comparable<DataValue>,
                DataFactoryItem
{
	/**
	 * Gets the value as a {@link DataArray}.
	 * @return The value as am array.
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
	 * Gets the value as a data.
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
	 * Gets the value as an integer.
	 * @return The value as an integer.
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
	 * Gets the internal object.
	 * @return The object of the value.
	 */
	Object getObject();

}

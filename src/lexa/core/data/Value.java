/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.core.data;

import java.util.Date;

/**
 *
 * @author william
 */
public interface Value
{
	/**
	Create a clone of the value
	@return a clone of the value
	*/
	Value clone();
	
	/**
	 * Gets the value as a {@link ValueArray}.
	 * @return The value as am array.
	 */
	ValueArray getArray();

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
	ValueType getType();

	/**
	 * Gets the internal value.
	 * @return The value of the item.
	 */
	Object getValue();
	
}

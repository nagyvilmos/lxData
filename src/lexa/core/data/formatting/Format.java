/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Format.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2015-03-05	WNW 2015-03		Add FormatLong.
 *================================================================================
 */
package lexa.core.data.formatting;

/**
 * An interface for the classes that provide formatting for serialising and deserialising
 * data.
 *
 * @param   <T>
 *          any type of object
 * @author  William
 * @since   2013-08
 */
public interface Format<T>
{

	/**
	 * Formats a value as a string.
	 * <p>The formatted string this returns should will in an equal object
	 * when calling {@link #fromString(java.lang.String) fromString(String)}.
	 * <p>Formally:
	 * <pre>value.equals(this.fromString(this.toString(value) == true</pre>
	 * @param   value
	 *          a value to be formatted
	 * @return  a value as a string
	 */
	public String toString(T value);

	/**
	 * Parse a string to get the value
	 * @param   string
	 *          a string to parse
	 * @return  the value held by {@code string} if it can be parsed,
	 *          otherwise {@code null}.
	 */
	public T fromString(String string);
}

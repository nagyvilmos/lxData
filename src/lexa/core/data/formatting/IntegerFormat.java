/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * IntegerFormat.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 30-03-2015	WNW	2015-03		Refactor FormatInteger -> IntegerFormat
 *================================================================================
 */
package lexa.core.data.formatting;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Helper for serialising {@link Integer} values.
 * @author William
 * @since 2013-08
 */
public class IntegerFormat
		implements Format<Integer>
{
	/** number formatter to use */
	private final NumberFormat numberFormat;

	/**
	Create an integer formatter using the default ENGLISH local
	@see Locale#ENGLISH
	*/
	public IntegerFormat()
	{
		this(Locale.ENGLISH);
	}

	/**
	Create an integer formatter for a locale.
	@param locale the local to provide formatting for.
	*/
	public IntegerFormat(Locale locale)
	{
		this.numberFormat = NumberFormat.getIntegerInstance(locale);
	}

	@Override
	public String toString(Integer value)
	{
		return this.numberFormat.format(value);
	}

	@Override
	public Integer fromString(String string)
	{

		try
		{
			return this.numberFormat.parse(string)
					.intValue();
		}
		catch (Exception ex)
		{
			// if we can't fromString it, return null
			return null;
		}
	}
}

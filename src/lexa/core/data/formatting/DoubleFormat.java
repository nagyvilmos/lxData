/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DoubleFormat.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 *================================================================================
 */
package lexa.core.data.formatting;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Helper for serialising {@link Double} values.
 * @author William
 * @since 2015-03
 */
public class DoubleFormat
		implements Format<Double>
{
	/** number formatter to use */
	private NumberFormat numberFormat;

	/**
	Create a double formatter using the default ENGLISH local
	@see Locale#ENGLISH
	*/
	public DoubleFormat()
	{
		this(Locale.ENGLISH);
	}

	/**
	Create a double formatter for a locale.
	@param locale the local to provide formatting for.
	*/
	public DoubleFormat(Locale locale)
	{
		this.numberFormat = NumberFormat.getNumberInstance(locale);
	}

	@Override
	public String toString(Double value)
	{
		return this.numberFormat.format(value);
	}

	@Override
	public Double fromString(String string)
	{

		try
		{
			return this.numberFormat.parse(string)
					.doubleValue();
		}
		catch (Exception ex)
		{
			// if we can't fromString it, return null
			return null;
		}
	}
}
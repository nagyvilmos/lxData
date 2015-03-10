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
 * @since 2013-08
 */
public class DoubleFormat
		implements Format<Double>
{

	private NumberFormat numberFormat;

	public DoubleFormat()
	{
		this(Locale.ENGLISH);
	}

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
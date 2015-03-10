/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * FormatInteger.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * DD-MON-YY    ??
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
public class FormatInteger
		implements Format<Integer>
{

	private final NumberFormat numberFormat;

	public FormatInteger()
	{
		this(Locale.ENGLISH);
	}

	public FormatInteger(Locale locale)
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

/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * FormatLong.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2015
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
 * Helper for serialising {@link Long} values.
 * @author William
 * @since 2015-03
 */
public class FormatLong
		implements Format<Long>
{

	private final NumberFormat numberFormat;

	public FormatLong()
	{
		this(Locale.ENGLISH);
	}

	public FormatLong(Locale locale)
	{
		this.numberFormat = NumberFormat.getNumberInstance(locale);
	}

	@Override
	public String toString(Long value)
	{
		return this.numberFormat.format(value);
	}

	@Override
	public Long fromString(String string)
	{

		try
		{
			return this.numberFormat.parse(string)
					.longValue();
		}
		catch (Exception ex)
		{
			// if we can't fromString it, return null
			return null;
		}
	}
}

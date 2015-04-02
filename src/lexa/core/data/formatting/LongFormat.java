/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * LongFormat.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2015
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 30-03-2015	WNW	2015-03		Refactor FormatLong -> LongFormat
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
public class LongFormat
		implements Format<Long>
{
	/** number formatter to use */
	private final NumberFormat numberFormat;

	/**
	Create a long formatter using the default ENGLISH local
	@see Locale#ENGLISH
	*/
	public LongFormat()
	{
		this(Locale.ENGLISH);
	}

	/**
	Create a long formatter for a locale.
	@param locale the local to provide formatting for.
	*/
	public LongFormat(Locale locale)
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

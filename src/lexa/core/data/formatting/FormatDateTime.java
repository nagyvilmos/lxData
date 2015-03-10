/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * FormatDateTime.java
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Helper for serialising the date and time values for a {@link java.util.Date} object.
 * @author  William
 * @since   2013-08
 */
public class FormatDateTime
		implements Format<Date>
{

	/** Format for writing the date */
	private final DateFormat dateFormat;

	/**
	 * Default constructor using a format of {@code "yyyy-MM-dd HH:mm:ss.SSS Z"}.
	 * <p>This is the format used by the {@link FormatCombined}
	 */
	public FormatDateTime()
	{
		this("yyyy-MM-dd HH:mm:ss.SSS Z");
	}

	/**
	 * Create a formatter for dates using the given format.
	 *
	 * @param   format
	 *          a valid format as described in {@link SimpleDateFormat}.
	 */
	public FormatDateTime(String format)
	{
		this(new SimpleDateFormat(format));
	}

	public FormatDateTime(int dateStyle, int timeStyle)
	{
		this(DateFormat.getDateTimeInstance(dateStyle, timeStyle));
	}

	public FormatDateTime(int dateStyle, int timeStyle, Locale locale)
	{
		this(DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale));
	}

	private FormatDateTime(DateFormat format)
	{
		this.dateFormat = format;
	}

	@Override
	public String toString(Date value)
	{
		return this.dateFormat.format(value);
	}

	@Override
	public Date fromString(String string)
	{
		try
		{
			return this.dateFormat.parse(string);
		}
		catch (Exception ex)
		{
			// if we can't fromString it, return null
			return null;
		}
	}
}

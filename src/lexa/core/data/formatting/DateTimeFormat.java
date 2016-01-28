/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DateTimeFormat.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 30-03-2015	WNW	2015-03		Refactor FormatDateTime -> DateTimeFormat
 * 2016-01-28   WNW 16-01       Update javadoc.
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
public class DateTimeFormat
		implements Format<Date>
{

	/** Format for writing the date */
	private final DateFormat dateFormat;

	/**
	 * Default constructor using a format of {@code "yyyy-MM-dd HH:mm:ss.SSS Z"}.
	 */
	public DateTimeFormat()
	{
		this("yyyy-MM-dd HH:mm:ss.SSS Z");
	}

	/**
	 * Create a formatter for dates using the given format.
	 *
	 * @param   format
	 *          a valid format as described in {@link SimpleDateFormat}.
	 */
	public DateTimeFormat(String format)
	{
		this(new SimpleDateFormat(format));
	}

	/**
	Create a formatter for dates using the given styles
	@param dateStyle the data style
	@param timeStyle the time style
	*/
	public DateTimeFormat(int dateStyle, int timeStyle)
	{
		this(DateFormat.getDateTimeInstance(dateStyle, timeStyle));
	}

	/**
	Create a formatter for dates using the given styles
	@param dateStyle the data style
	@param timeStyle the time style
	@param locale the locale for the format
	*/
	public DateTimeFormat(int dateStyle, int timeStyle, Locale locale)
	{
		this(DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale));
	}

	/**
	Create a formatter for dates using the java format
	@param format a core format object
	*/
	private DateTimeFormat(DateFormat format)
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

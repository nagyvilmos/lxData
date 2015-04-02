/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * CombinedDateTimeFormat.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 30-03-2015	WNW	2015-03		Refactor FormatCombinedDateTime -> CombinedDateTimeFormat
 *================================================================================
 */
package lexa.core.data.formatting;

import java.util.Date;

/**
 * Helper for serialising {@link Date} values as dates, times or both.
 * @author William
 * @since 2013-08
 */
public class CombinedDateTimeFormat
		implements Format<Date>
{
	/** formatting for a date and time */
	public final DateTimeFormat dateTime = new DateTimeFormat();
	/** formatting for a date only */
	public final DateTimeFormat date = new DateTimeFormat("yyyy-MM-dd");
	/** formatting for a time only */
	public final DateTimeFormat time = new DateTimeFormat("HH:mm:ss.SSS Z");

	@Override
	public String toString(Date value)
	{
		return this.dateTime.toString(value);
	}

	@Override
	public Date fromString(String string)
	{
		Date value = this.dateTime.fromString(string);
		if (value == null)
		{
			value = this.date.fromString(string);
		}
		if (value == null)
		{
			value = this.time.fromString(string);
		}
		return value;
	}
}

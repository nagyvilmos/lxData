/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * FormatBoolean.java
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

import java.util.Date;

/**
 * Helper for serialising {@link Date} values as dates, times or both.
 * @author William
 * @since 2013-08
 */
public class FormatCombinedDateTime
		implements Format<Date>
{

	public final FormatDateTime dateTime = new FormatDateTime();
	public final FormatDateTime date = new FormatDateTime("yyyy-MM-dd");
	public final FormatDateTime time = new FormatDateTime("HH:mm:ss.SSS Z");

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

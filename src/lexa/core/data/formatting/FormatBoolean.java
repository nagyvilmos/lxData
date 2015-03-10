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

/**
 * Helper for serialising {@link Boolean} values.
 * @author William
 * @since 2013-08
 */
public class FormatBoolean
		implements Format<Boolean>
{

	/** the string representation for {@code false} */
	private final static String FALSE_STRING = "false";
	/** the string representation for {@code true} */
	private final static String TRUE_STRING = "true";

	@Override
	public String toString(Boolean value)
	{
		if (value)
		{
			return TRUE_STRING;
		}
		else
		{
			if (!value)
			{
				return FALSE_STRING;
			}
		}
		return null;
	}

	@Override
	public Boolean fromString(String string)
	{
		if (TRUE_STRING.equals(string))
		{
			return true;
		}
		if (FALSE_STRING.equals(string))
		{
			return false;
		}
		return null;
	}
}

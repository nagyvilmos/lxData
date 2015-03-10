/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * FormatCombined.java
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
 * Helper for serialising data values.
 * <p>An instance provides references to all the formats.  This is to circumvent
 * having multiple references in the code base where multiple types are required.
 *
 * @author William
 * @since 2013-08
 */
public class FormatCombined
{

	/** formatting for a {@link Boolean} object */
	public final FormatBoolean booleanFormat = new FormatBoolean();
	/** formatting for a {@link java.util.Date} object */
	public final FormatCombinedDateTime dateFormat = new FormatCombinedDateTime();
	/** formatting for a {@link Double} object */
	public final DoubleFormat doubleFormat = new DoubleFormat();
	/** formatting for an {@link Integer} object */
	public final FormatInteger integerFormat = new FormatInteger();
	/** formatting for a {@link Long} object */
	public final FormatLong longFormat = new FormatLong();
	/** formatting for a {@link String} object */
	public final FormatString stringFormat = new FormatString();
}
